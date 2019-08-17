package cnogueira.sparky.parser

import cnogueira.sparky.exceptions.TokenizeException
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
    internal fun `tokenize throws TokenizeException when passing invalid input`() {
        invalidInput.forEach {
            assertThrows<TokenizeException>("must trow for '$it'") { tokenizer.tokenize(it) }
        }
    }
}
