package ru.split50.bunnybooru.bunny

import com.fasterxml.jackson.annotation.JsonProperty

data class BunnySubmissionResponse(
    @JsonProperty("submission_id") val submissionId: String? = null,
    @JsonProperty("keywords") val keywords: List<Keyword>? = null,
    @JsonProperty("hidden") val hidden: String? = null,
    @JsonProperty("scraps") val scraps: String? = null,
    @JsonProperty("favorite") val favorite: String? = null,
    @JsonProperty("favorites_count") val favoritesCount: String? = null,
    @JsonProperty("create_datetime") val createDatetime: String? = null,
    @JsonProperty("create_datetime_usertime") val createDatetimeUsertime: String? = null,
    @JsonProperty("last_file_update_datetime") val lastFileUpdateDatetime: String? = null,
    @JsonProperty("last_file_update_datetime_usertime") val lastFileUpdateDatetimeUsertime: String? = null,
    @JsonProperty("username") val username: String? = null,
    @JsonProperty("user_id") val userId: String? = null,
    @JsonProperty("user_icon_file_name") val userIconFileName: String? = null,
    @JsonProperty("user_icon_url_large") val userIconUrlLarge: String? = null,
    @JsonProperty("user_icon_url_medium") val userIconUrlMedium: String? = null,
    @JsonProperty("user_icon_url_small") val userIconUrlSmall: String? = null,
    @JsonProperty("file_name") val fileName: String? = null,
    @JsonProperty("file_url_full") val fileUrlFull: String? = null,
    @JsonProperty("file_url_screen") val fileUrlScreen: String? = null,
    @JsonProperty("file_url_preview") val fileUrlPreview: String? = null,
    @JsonProperty("thumbnail_url_huge_noncustom") val thumbnailUrlHugeNoncustom: String? = null,
    @JsonProperty("thumbnail_url_large_noncustom") val thumbnailUrlLargeNoncustom: String? = null,
    @JsonProperty("thumbnail_url_medium_noncustom") val thumbnailUrlMediumNoncustom: String? = null,
    @JsonProperty("thumb_medium_noncustom_x") val thumbMediumNoncustomX: String? = null,
    @JsonProperty("thumb_medium_noncustom_y") val thumbMediumNoncustomY: String? = null,
    @JsonProperty("thumb_large_noncustom_x") val thumbLargeNoncustomX: String? = null,
    @JsonProperty("thumb_large_noncustom_y") val thumbLargeNoncustomY: String? = null,
    @JsonProperty("thumb_huge_noncustom_x") val thumbHugeNoncustomX: String? = null,
    @JsonProperty("thumb_huge_noncustom_y") val thumbHugeNoncustomY: String? = null,
    @JsonProperty("files") val files: List<File>? = null,
    @JsonProperty("pools") val pools: List<Pool>? = null,
    @JsonProperty("pools_count") val poolsCount: Int? = null,
    @JsonProperty("title") val title: String? = null,
    @JsonProperty("deleted") val deleted: String? = null,
    @JsonProperty("public") val public: String? = null,
    @JsonProperty("mimetype") val mimetype: String? = null,
    @JsonProperty("pagecount") val pagecount: String? = null,
    @JsonProperty("rating_id") val ratingId: String? = null,
    @JsonProperty("rating_name") val ratingName: String? = null,
    @JsonProperty("ratings") val ratings: List<Any>? = null,
    @JsonProperty("submission_type_id") val submissionTypeId: String? = null,
    @JsonProperty("type_name") val typeName: String? = null,
    @JsonProperty("guest_block") val guestBlock: String? = null,
    @JsonProperty("friends_only") val friendsOnly: String? = null,
    @JsonProperty("comments_count") val commentsCount: String? = null,
    @JsonProperty("views") val views: String? = null,
) {
    data class Pool(
        @JsonProperty("pool_id")
        val poolId: String,
        val name: String,
    )

    data class Keyword(
        @JsonProperty("keyword_id") val keywordId: String? = null,
        @JsonProperty("keyword_name") val keywordName: String? = null,
        @JsonProperty("contributed") val contributed: String? = null,
        @JsonProperty("submissions_count") val submissionsCount: String? = null,
    )

    data class File(
        @JsonProperty("file_id") val fileId: String? = null,
        @JsonProperty("file_name") val fileName: String? = null,
        @JsonProperty("file_url_full") val fileUrlFull: String? = null,
        @JsonProperty("file_url_screen") val fileUrlScreen: String? = null,
        @JsonProperty("file_url_preview") val fileUrlPreview: String? = null,
        @JsonProperty("mimetype") val mimetype: String? = null,
        @JsonProperty("submission_id") val submissionId: String? = null,
        @JsonProperty("user_id") val userId: String? = null,
        @JsonProperty("submission_file_order") val submissionFileOrder: String? = null,
        @JsonProperty("full_size_x") val fullSizeX: String? = null,
        @JsonProperty("full_size_y") val fullSizeY: String? = null,
        @JsonProperty("screen_size_x") val screenSizeX: String? = null,
        @JsonProperty("screen_size_y") val screenSizeY: String? = null,
        @JsonProperty("preview_size_x") val previewSizeX: String? = null,
        @JsonProperty("preview_size_y") val previewSizeY: String? = null,
        @JsonProperty("initial_file_md5") val initialFileMd5: String? = null,
        @JsonProperty("full_file_md5") val fullFileMd5: String? = null,
        @JsonProperty("large_file_md5") val largeFileMd5: String? = null,
        @JsonProperty("small_file_md5") val smallFileMd5: String? = null,
        @JsonProperty("thumbnail_md5") val thumbnailMd5: String? = null,
        @JsonProperty("deleted") val deleted: String? = null,
        @JsonProperty("create_datetime") val createDatetime: String? = null,
        @JsonProperty("create_datetime_usertime") val createDatetimeUsertime: String? = null,
        @JsonProperty("thumbnail_url_huge_noncustom") val thumbnailUrlHugeNoncustom: String? = null,
        @JsonProperty("thumbnail_url_large_noncustom") val thumbnailUrlLargeNoncustom: String? = null,
        @JsonProperty("thumbnail_url_medium_noncustom") val thumbnailUrlMediumNoncustom: String? = null,
        @JsonProperty("thumb_medium_noncustom_x") val thumbMediumNoncustomX: String? = null,
        @JsonProperty("thumb_medium_noncustom_y") val thumbMediumNoncustomY: String? = null,
        @JsonProperty("thumb_large_noncustom_x") val thumbLargeNoncustomX: String? = null,
        @JsonProperty("thumb_large_noncustom_y") val thumbLargeNoncustomY: String? = null,
        @JsonProperty("thumb_huge_noncustom_x") val thumbHugeNoncustomX: String? = null,
        @JsonProperty("thumb_huge_noncustom_y") val thumbHugeNoncustomY: String? = null,
    )
}