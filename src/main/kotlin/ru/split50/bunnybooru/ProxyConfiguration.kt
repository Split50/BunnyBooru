package ru.split50.bunnybooru

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties("proxy")
data class ProxyConfiguration(
    val enabled: Boolean = false,
    val host: String = "",
    val port: Int = 0,
)