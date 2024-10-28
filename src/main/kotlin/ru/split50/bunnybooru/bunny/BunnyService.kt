package ru.split50.bunnybooru.bunny

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonTypeRef
import com.fasterxml.jackson.module.kotlin.readValue
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import org.springframework.stereotype.Component
import ru.split50.bunnybooru.ProxyConfiguration
import java.io.File
import java.io.InputStream
import java.net.InetSocketAddress
import java.nio.file.Files
import java.time.Duration

@Component
class BunnyService(
    private val mapper: ObjectMapper,
    private val proxyConfiguration: ProxyConfiguration,
) {
    private val client = OkHttpClient.Builder()
        .callTimeout(Duration.ofSeconds(30))
        .readTimeout(Duration.ofSeconds(30))
        .connectTimeout(Duration.ofSeconds(30))
        .writeTimeout(Duration.ofSeconds(30))
        .apply {
            if (proxyConfiguration.enabled) proxy(
                java.net.Proxy(
                    java.net.Proxy.Type.SOCKS,
                    InetSocketAddress(proxyConfiguration.host, proxyConfiguration.port)
                )
            )
        }
        .build()
    private val postCache = hashMapOf<PostSearch, List<BunnySubmissionResponse>>()

    private data class PostSearch(
        val sid: String,
        val query: String,
        val page: Int,
        val limit: Int,
    )

    fun findPosts(sid: String, query: String, page: Int, limit: Int): List<BunnySubmissionResponse> {
        val search = PostSearch(sid, query, page, limit)

        try {
            return postCache.computeIfAbsent(search, ::_findPosts)
        } catch (ex: Exception) {
            postCache.remove(search)
            throw ex
        }
    }

    private fun _findPosts(search: PostSearch): List<BunnySubmissionResponse> {
        val (sid, query, page, limit) = search
        val formBuilder = FormBody.Builder()
            .add("sid", sid)
            .add("page", page.toString())
            .add("submissions_per_page", limit.toString())
            .add("submission_ids_only", "yes")
            .add("type", "1,2,3,4,5,13")


        val tags = query.split(" ").toMutableSet()

        val artistName = tags.find { it.startsWith("a:") }
        if (artistName != null) {
            tags -= artistName
            formBuilder.add("username", artistName.substring("a:".length))
        }
        val pool = tags.find { it.startsWith("pool:") }
        if (pool != null) {
            tags -= pool
            val normalizedPool = pool.substring("pool:".length).substringBefore(":")
            formBuilder.add("pool_id", normalizedPool)
        }

        val processedQuery = tags.joinToString(",")
        formBuilder.add("text", processedQuery)

        val request = Request.Builder()
            .url("https://inkbunny.net/api_search.php")
            .post(formBuilder.build())
            .build()
        val response = execute<BunnySearchResponse<BunnySearchRow>>(request, jacksonTypeRef())
        if (page > response.pagesCount!!) return listOf()

        val submissionIds = response.submissions.map { it.submissionId }
        val submissions = fetchFullSubmissionData(submissionIds)

        val index = submissionIds.withIndex().associate { it.value to it.index }
        val sortedSubmissions = submissions.sortedBy { index[it.submissionId!!] }

        return sortedSubmissions
    }


    private fun fetchFullSubmissionData(submissionIds: List<String>): List<BunnySubmissionResponse> {
        val request = Request.Builder()
            .url("https://inkbunny.net/api_submissions.php")
            .post(
                FormBody.Builder()
                    .add("sid", "foPkrlfXFlv794si7XZT7sSJXK")
                    .add("submission_ids", submissionIds.joinToString(","))
                    .add("show_pools", "yes")
                    .build()
            )
            .build()

        val clientResponse = execute<BunnySearchResponse<BunnySubmissionResponse>>(request, jacksonTypeRef())

        return clientResponse.submissions
    }

    private fun <T : Any> execute(request: Request, reference: TypeReference<T>): T {
        return client.newCall(request).execute().use { clientResponse ->
            val rawResponse = clientResponse.body?.string()!!

            val node = mapper.readValue<JsonNode>(rawResponse)
            if (node.has("error_code")) {
                throw mapper.convertValue(node, BunnyError::class.java)
            }


            mapper.convertValue(node, reference)
        }
    }

    fun imageStream(original: String): InputStream {
        val response = client.newCall(
            Request.Builder()
                .url(original)
                .get()
                .build()
        ).execute()

        return response.body?.byteStream() ?: throw IllegalStateException("No body")
    }

    fun findSid(login: String): String? {
        val folder = File("users")
        val usernameFile = File(folder, login)
        if (!usernameFile.isFile) return null

        return Files.readString(usernameFile.toPath())
    }

    fun logIn(login: String, password: String): String {
        val folder = File("users")
        if (!folder.isDirectory) folder.mkdir()

        val file = File(folder, login)

        val request = Request.Builder()
            .url("https://inkbunny.net/api_login.php")
            .post(
                FormBody.Builder()
                    .add("username", login)
                    .add("password", password)
                    .build()
            )
            .build()

        val auth = execute<BunnyAuthResponse>(request, jacksonTypeRef())

        Files.writeString(file.toPath(), auth.sid)

        return auth.sid
    }

}