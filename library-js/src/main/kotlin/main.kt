import com.github.felipehjcosta.marvelclient.createApplicationApi
import kotlin.browser.window

val marvelPrivateKey: String = js("MARVEL_PRIVATE_KEY")
val marvelPublicKey: String = js("MARVEL_PUBLIC_KEY")

@JsName("libFunction")
fun libFunction() {
    createApplicationApi(marvelPrivateKey, marvelPublicKey).fetchCharacters(::showContent)
}

fun showContent(content: String) {
    val helloWorld = window.document.createElement("p")
    helloWorld.textContent = content
    window.document.body?.appendChild(helloWorld)
}