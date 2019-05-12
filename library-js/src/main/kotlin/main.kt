import com.github.felipehjcosta.marvelclient.ApplicationApi

val marvelPrivateKey: String = js("MARVEL_PRIVATE_KEY")
val marvelPublicKey: String = js("MARVEL_PUBLIC_KEY")

@JsName("libFunction")
fun libFunction() {
    ApplicationApi(marvelPrivateKey, marvelPublicKey).about {
        println("about")
        println(it)
    }
}