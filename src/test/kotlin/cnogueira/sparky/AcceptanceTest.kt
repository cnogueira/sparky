package cnogueira.sparky

import cnogueira.sparky.compiler.XispaCompiler
import cnogueira.sparky.parser.Parser
import cnogueira.sparky.parser.Tokenizer
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class AcceptanceTest {

    private val xispa = XispaCompiler(Parser(Tokenizer()))

    @Test
    internal fun `can process integer arithmetic`() {
        assertEquals("401", xispa.evaluate(" 1 + 2  * 200"))
    }
}
