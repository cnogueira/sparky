package cnogueira.sparky.parser

import cnogueira.sparky.grammar.Expression

class Parser(private val tokenizer: Tokenizer) {
    fun parse(input: String): Expression {
        val tokens = tokenizer.tokenize(input)

        TODO("build expression from token list")
    }
}
