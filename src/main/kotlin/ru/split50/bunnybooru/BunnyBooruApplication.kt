package ru.split50.bunnybooru

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableConfigurationProperties
@ConfigurationPropertiesScan
class BunnyBooruApplication

fun main(args: Array<String>) {
    runApplication<BunnyBooruApplication>(*args)
}
