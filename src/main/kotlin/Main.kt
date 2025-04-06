package org.example

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Serializable
data class DiscordMessage(val content: String)

fun main() = runBlocking {
    val webhookUrl = System.getenv("DISCORD_WEBHOOK_URL")

    val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
            })
        }
    }

    println("Sending message to Discord...")

    val response = client.post(webhookUrl) {
        contentType(ContentType.Application.Json)
        setBody(DiscordMessage("👋 Hello from Ktor Kotlin app!"))
    }

    println("Message sent. Status: ${response.status}")
    client.close()
}