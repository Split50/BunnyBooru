package ru.split50.bunnybooru.bunny

import com.fasterxml.jackson.annotation.JsonProperty

data class BunnySearchResponse<T>(
    val submissions: List<T>,
    @JsonProperty("pages_count")
    val pagesCount: Int? = null,
)