package cnogueira.sparky.compiler

import cnogueira.sparky.lexer.Lexer
import cnogueira.sparky.parser.Parser

class XispaCompiler(private val lexer: Lexer, private val parser: Parser) {

    fun evaluate(input: String): String {
        val tokens = lexer.tokenize(input)
        val expression = parser.parseExpression(tokens)

        return expression.value().toString()
    }
}
