package ru.freestyle.keygen

import io.ktor.client.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import ru.freestyle.keygen.github.PasswordContext
import ru.freestyle.keygen.keytab.KeyTab

fun main() {
    println("Starting Droch Passowrd")
    runBlocking {
        PasswordContext().generatePasswords()
    }
    KeyTab().start()
}
val httpClient = HttpClient(OkHttp) {
     install (ContentNegotiation) {
        json(Json {
            encodeDefaults = true
            ignoreUnknownKeys = true
        })
    }
}