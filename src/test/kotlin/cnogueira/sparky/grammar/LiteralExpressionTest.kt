package cnogueira.sparky.grammar

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test

internal class LiteralExpressionTest {
    @Test
    internal fun `returns value as value as is`() {
        val expectedValue = 1988
        val literalExpression = LiteralExpression(expectedValue)

        assertThat(literalExpression.value()).isEqualTo(expectedValue)
    }
}
