package cnogueira.sparky.parser

import cnogueira.sparky.exceptions.TokenizeException

class Tokenizer {

    private companion object Patterns {
        private val whitespace = Regex("\\s")

        private val recognizedPatterns = listOf(
            Regex("[1-9]\\d*")
        )
    }

    fun tokenize(input: String): List<String> {
        if (recognizedPatterns.any { it.matches(input) }) {
            return listOf(input)
        }

        throw TokenizeException("invalid input: '$input'")
    }
}
