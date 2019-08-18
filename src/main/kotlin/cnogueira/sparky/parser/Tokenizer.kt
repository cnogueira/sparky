package cnogueira.sparky.parser

import cnogueira.sparky.exceptions.TokenizeException

class Tokenizer {

    private companion object {
        const val MAX_DISPLAYABLE_INPUT_LENGTH = 10

        val whitespace = Regex("\\A\\s+")
        val integer = Regex("\\A[1-9]\\d*")
    }

    fun tokenize(input: String): List<Token> {
        return recursivelyTokenize(input, ArrayList(), 1, 0)
            .filter { it !is WhitespacesToken }
    }

    private fun recursivelyTokenize(
        input: String,
        tokenList: MutableList<Token>,
        lineNumber: Int,
        cursorPosition: Int
    ): List<Token> {

        if (input.isEmpty()) {
            return returnTokenListWithEof(tokenList, lineNumber, cursorPosition)
        }

        val nextToken: Token = parseNextToken(input, lineNumber, cursorPosition)

        tokenList.add(nextToken)

        return recursivelyTokenize(
            input.substring(nextToken.length),
            tokenList,
            if (nextToken is NewLineToken) lineNumber + 1 else lineNumber,
            cursorPosition + nextToken.length
        )
    }

    private fun parseNextToken(input: String, lineNumber: Int, cursorPosition: Int): Token {
        if (startsWithWhitespaces(input)) {
            return parseWhitespacesToken(input, lineNumber, cursorPosition)
        }

        if (startsWithInteger(input)) {
            return parseIntToken(input, lineNumber, cursorPosition)
        }

        return throwTokenizeException(input)
    }

    private fun throwTokenizeException(input: String): Token {
        val displayableInput = if (input.length > MAX_DISPLAYABLE_INPUT_LENGTH) {
            input.substring(0, 10) + "..."
        } else {
            input
        }

        throw TokenizeException("Unrecognized input: $displayableInput")
    }

    private fun startsWithInteger(input: String) = integer.containsMatchIn(input)

    private fun parseIntToken(input: String, lineNumber: Int, start: Int): IntToken {
        val value = integer.find(input)!!.value

        return IntToken(lineNumber, start, value.length, value.toInt())
    }

    private fun returnTokenListWithEof(
        tokenList: MutableList<Token>,
        lineNumber: Int,
        cursorPosition: Int
    ): List<Token> {
        tokenList.add(EofToken(lineNumber, cursorPosition))

        return tokenList
    }

    private fun startsWithWhitespaces(input: String) = whitespace.containsMatchIn(input)

    private fun parseWhitespacesToken(input: String, lineNumber: Int, start: Int): WhitespacesToken {
        val whitespaces = whitespace.find(input)!!.value

        return WhitespacesToken(lineNumber, start, whitespaces.length, whitespaces)
    }
}
