package cnogueira.sparky.parser

import cnogueira.sparky.exceptions.TokenizeException

class Tokenizer {

    private companion object Patterns {
        private val whitespace = Regex("\\s+")

        private val recognizedPatterns = mapOf(
            Regex("[1-9]\\d*") to { token: String -> IntToken(token.toInt()) }
        )
    }

    fun tokenize(input: String): List<Token> {
        return input.split(whitespace)
            .map { tokenString -> toTokenObject(tokenString) }
            .plus(NewLineToken())
    }

    private fun toTokenObject(token: String): Token {
        val matchingRegex = findMatchingPattern(token)

        return tokenBuilderFrom(matchingRegex)(token)
    }

    private fun findMatchingPattern(token: String): Regex =
        recognizedPatterns.keys.find { it.matches(token) } ?: throw TokenizeException("Found illegal token: '$token'")

    private fun tokenBuilderFrom(regex: Regex): (String) -> Token =
        recognizedPatterns[regex] ?: throw IllegalArgumentException("unrecognized regex pattern: $regex")
}
