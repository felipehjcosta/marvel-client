package sample

import kotlin.test.Test
import kotlin.test.assertTrue

class SampleTestsIOSSim {
    @Test
    fun testHello() {
        assertTrue("iOSSim" in hello())
    }
}