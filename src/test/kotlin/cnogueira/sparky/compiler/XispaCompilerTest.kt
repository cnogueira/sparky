package cnogueira.sparky.compiler

import cnogueira.sparky.exceptions.ExpressionEvaluationException
import cnogueira.sparky.exceptions.ParseException
import cnogueira.sparky.grammar.Expression
import cnogueira.sparky.parser.Parser
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
internal class XispaCompilerTest {

    @MockK
    lateinit var parser: Parser

    @MockK
    lateinit var parsedExpression: Expression

    @InjectMockKs
    lateinit var xispa: XispaCompiler

    @Test
    fun `evaluates single value expressions`() {
        every { parser.parse("2") } returns parsedExpression
        every { parsedExpression.value() } returns 2

        assertEquals("2", xispa.evaluate("2"))
    }

    @Test
    internal fun `evaluate throws exception when parser throws exception`() {
        every { parser.parse(any()) } throws ParseException("Expecting Expression")

        assertThrows<ParseException> { xispa.evaluate("it really doesn't matter") }
    }

    @Test
    internal fun `evaluate throws if the expression value method throws`() {
        every { parser.parse(any()) } returns parsedExpression
        every { parsedExpression.value() } throws ExpressionEvaluationException()

        assertThrows<ExpressionEvaluationException> { xispa.evaluate("it really doesn't matter") }
    }
}
