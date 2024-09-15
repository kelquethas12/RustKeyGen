package ru.freestyle.keygen.github

import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import ru.freestyle.keygen.httpClient

val passwordKeyGen = mutableListOf<String>()

class PasswordContext {


    suspend fun generatePasswords() {
            try {
                println("Making request to the server...")
                val response = httpClient.get("https://raw.githubusercontent.com/danielmiessler/SecLists/master/Passwords/Common-Credentials/four-digit-pin-codes-sorted-by-frequency-withcount.csv")

                if (response.status.isSuccess()) {
                    val passwords = response.bodyAsText()

                    if (passwords.isNotEmpty()) {
                        val passwordLines = passwords.split("\n")
                        for (passwordLine in passwordLines) {
                            passwordKeyGen.add(passwordLine.split(",")[0])
                        }
                    } else {
                        println("Response body is empty.")
                    }
                    passwordKeyGen.reversed()
                } else {
                    println("Failed to retrieve data. Status: ${response.status}")
                }
            } catch (e: Exception) {
                println("Exception occurred: ${e.message}")
                e.printStackTrace()
            } finally {
                httpClient.close()
            }

    }
}
