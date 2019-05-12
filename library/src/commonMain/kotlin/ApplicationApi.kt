package com.github.felipehjcosta.marvelclient


import com.soywiz.klock.DateTime
import com.soywiz.krypto.md5
import io.ktor.client.HttpClient
import io.ktor.client.features.logging.DEFAULT
import io.ktor.client.features.logging.LogLevel
import io.ktor.client.features.logging.Logger
import io.ktor.client.features.logging.Logging
import io.ktor.client.request.get
import io.ktor.client.request.url
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.io.core.toByteArray

internal expect val ApplicationDispatcher: CoroutineDispatcher

class ApplicationApi(private val marvelPrivateKey: String, private val marvelPublicKey: String) {

    private val timestamp: String
        get() {
            return (DateTime.now().unixMillisLong / 1000L).toString()
        }

    private val client = HttpClient {
        install(Logging) {
            logger = Logger.DEFAULT
            level = LogLevel.HEADERS
        }
    }

    private var baseAddress = "https://gateway.marvel.com/v1/public/characters"

    fun about(callback: (String) -> Unit) {
        GlobalScope.apply {
            launch(ApplicationDispatcher) {
                val result: String = client.get {
                    val timestamp = timestamp
                    val fullUrl = "$baseAddress?limit=20&offset=0&ts=$timestamp&apikey=$marvelPublicKey&hash=${generateHash(timestamp)}"
                    url(fullUrl)
                }

                callback(result)
            }
        }
    }

    private fun generateHash(timestamp: String): String {
        return (timestamp + marvelPrivateKey + marvelPublicKey).toByteArray().md5().asUByteArray()
            .joinToString("") { it.toString(16).padStart(2, '0') }
    }
}