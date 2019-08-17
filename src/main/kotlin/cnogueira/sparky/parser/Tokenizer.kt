package cnogueira.sparky.parser

import cnogueira.sparky.exceptions.TokenizeException

class Tokenizer {

    private companion object Patterns {
        private val whitespace = Regex("\\s+")

        private val recognizedPatterns = listOf(
            Regex("[1-9]\\d*")
        )
    }

    fun tokenize(input: String): List<String> {
        val tokens = input.split(whitespace)

        if (tokens.any { token -> recognizedPatterns.none { it.matches(token) } }) {
            throw TokenizeException("invalid input: '$input'")
        }

        return tokens
    }
}
