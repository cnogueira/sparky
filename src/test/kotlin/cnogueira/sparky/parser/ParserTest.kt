package cnogueira.sparky.parser

import cnogueira.sparky.grammar.LiteralExpression
import cnogueira.sparky.lexer.IntToken
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class ParserTest {

    private lateinit var parser: Parser

    @BeforeEach
    internal fun setUp() {
        parser = Parser()
    }

    @Test
    internal fun `parse single integer expression`() {
        val tokenList = listOf(IntToken(1, 0, 4, 1337))

        assertEquals(LiteralExpression(1337), parser.parse(tokenList))
    }
}
