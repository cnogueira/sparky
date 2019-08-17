package cnogueira.sparky.parser

import cnogueira.sparky.exceptions.TokenizeException
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
internal class ParserTest {

    @MockK
    lateinit var tokenizer: Tokenizer

    @InjectMockKs
    lateinit var parser: Parser

    @Test
    internal fun `parse throws when the tokenizer throws`() {
        every { tokenizer.tokenize(any()) } throws TokenizeException("with some message")

        assertThrows<TokenizeException> { parser.parse("it really doesn't matter") }
    }
}