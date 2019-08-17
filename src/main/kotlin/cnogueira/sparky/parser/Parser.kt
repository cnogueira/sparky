package cnogueira.sparky.parser

import cnogueira.sparky.exceptions.ParseException
import cnogueira.sparky.grammar.Expression
import cnogueira.sparky.grammar.LiteralExpression

class Parser(private val tokenizer: Tokenizer) {
    fun parse(input: String): Expression {
        val tokens = tokenizer.tokenize(input)

        if (tokens.isNotEmpty()) {
            val token = tokens.first()
            if (token is IntToken) {
                return LiteralExpression(token.value)
            }
        }

        throw ParseException("Expecting an expression")
    }
}
