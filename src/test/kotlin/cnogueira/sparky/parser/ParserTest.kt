package cnogueira.sparky.parser

import assertk.assertThat
import assertk.assertions.hasMessage
import assertk.assertions.isEqualTo
import assertk.assertions.isFailure
import assertk.assertions.isInstanceOf
import cnogueira.sparky.exceptions.ParseException
import cnogueira.sparky.grammar.BinarySumExpression
import cnogueira.sparky.grammar.LiteralExpression
import cnogueira.sparky.lexer.BinarySumOperatorToken
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

        assertEquals(LiteralExpression(1337), parser.parseExpression(tokenList))
    }

    @Test
    internal fun `throws when encounters two consecutive literals`() {
        val tokenList = listOf(
            IntToken(1, 0, 2, 12),
            IntToken(1, 3, 1, 7)
        )

        assertThat { parser.parseExpression(tokenList) }
            .isFailure()
            .isInstanceOf(ParseException::class)
            .hasMessage("[1:3..4]: Unexpected token: '7'")
    }

    @Test
    internal fun `parses binary sum expression`() {
        val tokenList = listOf(
            IntToken(1, 0, 2, 12),
            BinarySumOperatorToken(1, 3),
            IntToken(1, 5, 1, 7)
        )

        val expectedExpression = BinarySumExpression(
            left = LiteralExpression(12),
            right = LiteralExpression(7)
        )

        assertThat(parser.parseExpression(tokenList)).isEqualTo(expectedExpression)
    }
}
