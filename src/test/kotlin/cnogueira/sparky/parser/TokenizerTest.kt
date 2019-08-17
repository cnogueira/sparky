package cnogueira.sparky.parser

import cnogueira.sparky.exceptions.TokenizeException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

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
            assertThrows<TokenizeException>("must trow for '$it'") { tokenizer.tokenize(it) }
        }
    }

    @Test
    internal fun `recognizes single integer value`() {
        val expectedTokenList = listOf(IntToken(1337), NewLineToken())
        assertEquals(expectedTokenList, tokenizer.tokenize("1337"))
    }

    @Test
    internal fun `recognizes multiple integers`() {
        val expectedTokenList = listOf(
            IntToken(1),
            IntToken(2),
            IntToken(3),
            NewLineToken()
        )

        assertEquals(expectedTokenList, tokenizer.tokenize("1 2 3"))
    }
}
