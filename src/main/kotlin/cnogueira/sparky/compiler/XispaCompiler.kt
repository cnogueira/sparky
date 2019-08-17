package cnogueira.sparky.compiler

import cnogueira.sparky.parser.Parser

class XispaCompiler(val parser: Parser) {

    fun evaluate(input: String): String {
        val expression = parser.parse(input)

        return expression.value().toString()
    }
}
