package ru.split50.bunnybooru.bunny

import com.fasterxml.jackson.annotation.JsonProperty

data class BunnyError(
    @JsonProperty("error_code")
    val errorCode: Int,
    @JsonProperty("error_message")
    val errorMessage: String,
) : RuntimeException("InkBunny error $errorCode: $errorMessage")