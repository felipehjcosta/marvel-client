import com.github.felipehjcosta.marvelclient.ApplicationApi
import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.MockHttpResponse
import io.ktor.http.*
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

    private val httpMockEngine = MockEngine {
        when (url.baseUrl) {
            "https://gateway.marvel.com" -> {
                return@MockEngine if (
                    url.parameters["limit"]?.isNotBlank() == true
                    && url.parameters["offset"]?.isNotBlank() == true
                    && url.parameters["ts"]?.isNotBlank() == true
                    && url.parameters["apikey"]?.isNotBlank() == true
                    && url.parameters["hash"]?.isNotBlank() == true
                ) {
                    MockHttpResponse(
                        call,
                        HttpStatusCode.OK,
                        ByteReadChannel("{\"message\":\"Hello World!\"}".toByteArray(Charsets.UTF_8)),
                        headersOf("Content-Type" to listOf(ContentType.Text.JavaScript.toString()))
                    )
                } else {
                    error("Unhandled ${url.fullUrl}")
                }
            }
            else -> {
                error("Unhandled ${url.fullUrl}")
            }
        }
    }

    private val fakeClient = HttpClient(httpMockEngine)

    private val applicationApi = ApplicationApi(fakeClient, marvelPrivateKey, marvelPublicKey)

    @Test
    fun ensureFetchCharactersReturnsWithSuccess() = runTest {
        assertEquals("{\"message\":\"Hello World!\"}", applicationApi.fetchCharacters())
    }
}