import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.promise
import kotlin.coroutines.Continuation
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.coroutines.startCoroutine

fun launch(block: suspend () -> Unit) {
    block.startCoroutine(object : Continuation<Unit> {
        override val context: CoroutineContext get() = EmptyCoroutineContext
        override fun resumeWith(result: Result<Unit>) {}
//        override fun resume(value: Unit) {}
//        override fun resumeWithException(e: Throwable) { console.log("Coroutine failed: $e") }
    })
}

actual fun runTest(block: suspend () -> Unit): dynamic = launch(block)