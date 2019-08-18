package cnogueira.sparky.lexer

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isFailure
import assertk.assertions.isInstanceOf
import cnogueira.sparky.exceptions.TokenizeException
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class LexerTest {

    private val invalidInput = listOf(
        "a12b", "1home", "1234.5789b", "15.", "01", "10_0", "10_00", "10_0000", ".2.3", "1.2.3"
    )

    lateinit var lexer: Lexer

    @BeforeEach
    internal fun setUp() {
        lexer = Lexer()
    }

    @Test
    internal fun `throws TokenizeException when passing invalid input`() {
        invalidInput.forEach {
            assertThat { lexer.tokenize(it) }
                .isFailure()
                .isInstanceOf(TokenizeException::class)
        }
    }

    @Test
    internal fun `recognizes single integer value`() {
        val expectedTokenList = listOf(
            IntToken(1, 0, 4, 1337),
            EofToken(1, 4)
        )

        assertThat(lexer.tokenize("1337")).isEqualTo(expectedTokenList)
    }

    @Test
    internal fun `recognizes multiple integers`() {
        val expectedTokenList = listOf(
            IntToken(1, 0, 1, 1),
            IntToken(1, 2, 1, 2),
            IntToken(1, 4, 1, 3),
            EofToken(1, 5)
        )

        assertThat(lexer.tokenize("1 2 3")).isEqualTo(expectedTokenList)
    }

    @Test
    internal fun `ignores all sorts of whitespaces`() {
        val expectedTokenList = listOf(
            IntToken(1, 5, 5, 12345),
            EofToken(1, 10)
        )

        assertThat(lexer.tokenize(" \t \t\t12345")).isEqualTo(expectedTokenList)
    }

    @Test
    internal fun `it does not ignore new lines`() {
        val expectedTokenList = listOf(
            NewLineToken(1, 2),
            IntToken(2, 2, 5, 12345),
            EofToken(2, 7)
        )

        assertThat(lexer.tokenize(" \t\n\t\t12345")).isEqualTo(expectedTokenList)
    }

    @Test
    internal fun `recognizes binary sum`() {
        val expectedTokenList = listOf(
            IntToken(1, 0, 1, 1),
            BinarySumOperatorToken(1, 2),
            IntToken(1, 4, 1, 2),
            EofToken(1, 5)
        )

        assertThat(lexer.tokenize("1 + 2")).isEqualTo(expectedTokenList)
    }

    @Test
    internal fun `recognizes binary multiplication`() {
        val expectedTokenList = listOf(
            IntToken(1, 0, 1, 1),
            BinaryMultOperatorToken(1, 2),
            IntToken(1, 4, 1, 2),
            EofToken(1, 5)
        )

        assertThat(lexer.tokenize("1 * 2")).isEqualTo(expectedTokenList)
    }
}
