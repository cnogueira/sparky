package cnogueira.sparky

import cnogueira.sparky.compiler.XispaCompiler
import cnogueira.sparky.lexer.Lexer
import cnogueira.sparky.parser.Parser
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class AcceptanceTest {

    private val xispa = XispaCompiler(Lexer(), Parser())

    @Test
    internal fun `can process integer arithmetic`() {
        assertEquals("401", xispa.evaluate(" 1 + 2  * 200"))
    }
}
