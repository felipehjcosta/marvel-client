import com.github.felipehjcosta.marvelclient.ApplicationApi

@JsName("libFunction")
fun libFunction() {
    println("libFunction")
    ApplicationApi().about {
        println("about")
        println(it)
    }
}