package ru.split50.bunnybooru.booru

import org.apache.commons.io.FilenameUtils
import org.springframework.core.io.InputStreamResource
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import ru.split50.bunnybooru.bunny.BunnyError
import ru.split50.bunnybooru.bunny.BunnyService
import ru.split50.bunnybooru.bunny.BunnySubmissionResponse
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@RestController
class BooruAdapterController(
    private val bunnyService: BunnyService,
) {
    @GetMapping("/posts.json")
    fun posts(
        @RequestParam(defaultValue = "10") limit: Int,
        @RequestParam(defaultValue = "1") page: Int,
        @RequestParam(defaultValue = "") tags: String,
        @RequestParam(defaultValue = "guest") login: String,
        @RequestParam(name = "api_key", defaultValue = "") password: String,
    ): List<BooruRow> {
        if (limit == 1) return listOf()
        var sid = bunnyService.findSid(login)
        if (sid == null) sid = bunnyService.logIn(login, password)
        val result = try {
            bunnyService.findPosts(sid, tags, page, limit)
        } catch (ex: BunnyError) {
            if (ex.errorCode != 2) throw ex
            sid = bunnyService.logIn(login, password)

            bunnyService.findPosts(sid, tags, page, limit)
        }

        return result.flatMap {
            mapToBooru(it).reversed()
        }
    }

    @GetMapping("/image")
    fun image(
        @RequestParam original: String,
        @RequestParam(defaultValue = "1") fileNumber: Int,
    ): ResponseEntity<InputStreamResource> {
        return ResponseEntity.ok(InputStreamResource(bunnyService.imageStream(original)))
    }

    @ExceptionHandler(BunnyError::class)
    fun onBunnyException(
        ex: BunnyError,
    ): ResponseEntity<BooruError> {
        return ResponseEntity.badRequest().body(BooruError(false, ex.errorCode.toString(), ex.errorMessage))
    }

    private fun mapToBooru(row: BunnySubmissionResponse) = row.files!!.mapIndexedNotNull { fileNumber, file ->
        if (!file.mimetype!!.startsWith("image/")) return@mapIndexedNotNull null
        val extension = FilenameUtils.getExtension(file.fileName)
        val generalTags = row.keywords!!.map { it.keywordName!!.replace(" ", "_") }
        val artistTags = listOf(
            "a:${row.username}"
        )

        val metaTags = arrayListOf<String>()
        metaTags += (row.pools ?: listOf()).map {
            "pool:${it.poolId}:${it.name.replace(" ", "_")}"
        }
        val rating = when (row.ratingId!!) {
            "0" -> "s"
            "1" -> "q"
            "2" -> "e"
            else -> "e"
        }

        BooruRow(
            id = row.submissionId + "_$fileNumber",
            createdAt = row.createDatetime,
            uploaderId = row.userId,
            score = row.ratingId,
            source = "https://inkbunny.net/s/${row.submissionId}",
            imageWidth = file.screenSizeX,
            imageHeight = file.screenSizeY,

            tagCountGeneral = generalTags.size.toString(),
            tagStringGeneral = generalTags.joinToString(" "),

            tagStringArtist = artistTags.joinToString(" "),
            tagCountArtist = artistTags.size.toString(),

            favCount = row.favoritesCount,
            fileExt = extension,
            parentId = row.submissionId,
            hasChildren = row.files.size > 1,
            rating = rating,
            md5 = file.initialFileMd5,
            mediaAsset = BooruRow.MediaAsset(
                id = row.submissionId + "_${fileNumber}_" + "file",

                imageWidth = file.screenSizeX,
                imageHeight = file.screenSizeY,

                variants = listOf(
                    BooruRow.Variant(
                        type = "preview",
                        fileExt = extension,
                        width = file.previewSizeX,
                        height = file.previewSizeY,
                        url = file.fileUrlPreview?.imageUrl(fileNumber),
                    ),
                    BooruRow.Variant(
                        type = "screen",
                        fileExt = extension,
                        width = file.screenSizeX,
                        height = file.screenSizeY,
                        url = file.fileUrlScreen?.imageUrl()
                    )
                )
            ),
            largeFileUrl = file.fileUrlFull?.imageUrl(),
            fileUrl = file.fileUrlScreen?.imageUrl(),
            previewFileUrl = file.fileUrlPreview?.imageUrl(fileNumber),

            tagCountMeta = metaTags.size.toString(),
            tagStringMeta = metaTags.joinToString(" ")
        )
    }

    private fun String.imageUrl(fileNumber: Int = 1): String {
        return "/image?original=" + URLEncoder.encode(this, StandardCharsets.UTF_8) + "&fileNumber=$fileNumber"
    }
}