import com.github.felipehjcosta.marvelclient.ApplicationApi
import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.http.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.io.ByteReadChannel
import kotlinx.io.charsets.Charsets
import kotlinx.io.core.toByteArray
import kotlin.test.Test
import kotlin.test.assertEquals

class ApplicationApiTests {

    private val marvelPrivateKey = "MARVEL_PRIVATE_KEY"
    private val marvelPublicKey = "MARVEL_PUBLIC_KEY"

    private val Url.hostWithPortIfRequired: String get() = if (port == protocol.defaultPort) host else hostWithPort
    private val Url.baseUrl: String get() = "${protocol.name}://$hostWithPortIfRequired"
    private val Url.fullUrl: String get() = "${protocol.name}://$hostWithPortIfRequired$fullPath"

    private val fakeClient = HttpClient(MockEngine) {
        engine {
            addHandler {
                return@addHandler when (it.url.baseUrl) {
                    "https://gateway.marvel.com" -> {
                        if (
                            it.url.parameters["limit"]?.isNotBlank() == true
                            && it.url.parameters["offset"]?.isNotBlank() == true
                            && it.url.parameters["ts"]?.isNotBlank() == true
                            && it.url.parameters["apikey"]?.isNotBlank() == true
                            && it.url.parameters["hash"]?.isNotBlank() == true
                        ) {
                            respond(
                                content = ByteReadChannel("{\"message\":\"Hello World!\"}".toByteArray(Charsets.UTF_8)),
                                status = HttpStatusCode.OK,
                                headers = headersOf("Content-Type" to listOf(ContentType.Text.JavaScript.toString()))
                            )
                        } else {
                            error("Unhandled ${it.url.fullUrl}")
                        }
                    }
                    else -> {
                        error("Unhandled ${it.url.fullUrl}")
                    }
                }
            }
        }
    }

    private val applicationApi = ApplicationApi(fakeClient, marvelPrivateKey, marvelPublicKey)

    @Test
    fun ensureFetchCharactersExecuteWithSuccess() = runTest {
        assertEquals("{\"message\":\"Hello World!\"}", applicationApi.fetchCharacters())
    }

    @Test
    fun ensureFetchCharactersWithCallbackExecuteWithSuccess() = runTest {
        applicationApi.fetchCharacters {
            assertEquals("{\"message\":\"Hello World!\"}", it)
        }

        delay(2000L)
    }
}