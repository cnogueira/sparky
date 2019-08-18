package cnogueira.sparky.parser

import cnogueira.sparky.exceptions.ParseException
import cnogueira.sparky.grammar.Expression
import cnogueira.sparky.grammar.LiteralExpression
import cnogueira.sparky.lexer.IntToken
import cnogueira.sparky.lexer.Token

class Parser {
    fun parse(tokens: List<Token>): Expression {

        if (tokens.isNotEmpty()) {
            val token = tokens.first()
            if (token is IntToken) {
                return LiteralExpression(token.value)
            }
        }

        throw ParseException("Expecting an expression")
    }
}
