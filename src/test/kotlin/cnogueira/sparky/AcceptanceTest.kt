package cnogueira.sparky

import cnogueira.sparky.compiler.XispaCompiler
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class AcceptanceTest {

    private val xispa = XispaCompiler()

    @Test
    internal fun `can process integer arithmetic`() {
        assertEquals("401", xispa.evaluate(" 1 + 2  * 200"))
    }
}
