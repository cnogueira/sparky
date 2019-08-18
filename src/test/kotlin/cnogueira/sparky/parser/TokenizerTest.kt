package cnogueira.sparky.parser

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isFailure
import assertk.assertions.isInstanceOf
import cnogueira.sparky.exceptions.TokenizeException
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class TokenizerTest {

    private val invalidInput = listOf(
        "a12b", "1home", "1234.5789b", "15.", "01", "10_0", "10_00", "10_0000", ".2.3", "1.2.3"
    )

    lateinit var tokenizer: Tokenizer

    @BeforeEach
    internal fun setUp() {
        tokenizer = Tokenizer()
    }

    @Test
    internal fun `throws TokenizeException when passing invalid input`() {
        invalidInput.forEach {
            assertThat { tokenizer.tokenize(it) }
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

        assertThat(tokenizer.tokenize("1337")).isEqualTo(expectedTokenList)
    }

    @Test
    internal fun `recognizes multiple integers`() {
        val expectedTokenList = listOf(
            IntToken(1, 0, 1, 1),
            IntToken(1, 2, 1, 2),
            IntToken(1, 4, 1, 3),
            EofToken(1, 5)
        )

        assertThat(tokenizer.tokenize("1 2 3")).isEqualTo(expectedTokenList)
    }

    @Test
    internal fun `ignores all sorts of whitespaces`() {
        val expectedTokenList = listOf(
            IntToken(1, 5, 5, 12345),
            EofToken(1, 10)
        )

        assertThat(tokenizer.tokenize(" \t \r\t12345")).isEqualTo(expectedTokenList)
    }

    @Test
    internal fun `it does not ignore new lines`() {
        val expectedTokenList = listOf(
            NewLineToken(1, 2),
            IntToken(2, 2, 5, 12345),
            EofToken(2, 7)
        )

        assertThat(tokenizer.tokenize(" \t\n\r\t12345")).isEqualTo(expectedTokenList)
    }
}
