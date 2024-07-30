package ru.split50.bunnybooru.booru

class BooruError(
    val success: Boolean = false,
    val error: String,
    val message: String,
)