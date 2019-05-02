import com.github.felipehjcosta.marvelclient.ApplicationApi

@JsName("libFunction")
fun libFunction() {
    ApplicationApi().about {
        println("it")
    }
}