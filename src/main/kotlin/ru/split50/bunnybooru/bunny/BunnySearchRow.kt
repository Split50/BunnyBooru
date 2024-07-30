package ru.split50.bunnybooru.bunny

import com.fasterxml.jackson.annotation.JsonProperty

data class BunnySearchRow(
    @JsonProperty("submission_id") val submissionId: String,
)