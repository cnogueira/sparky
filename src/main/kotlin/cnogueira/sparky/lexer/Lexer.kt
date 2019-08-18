package cnogueira.sparky.lexer

import cnogueira.sparky.exceptions.TokenizeException

class Lexer {

    private companion object {
        const val MAX_DISPLAYABLE_INPUT_LENGTH = 10

        val whitespace = Regex("\\A[ \\t\\x0B\\f]+")
        val integer = Regex("\\A[1-9]\\d*")
        val newLine = Regex("\\A\\n")
        val binarySumOperator = Regex("\\A\\+")
        val binaryMultiplicationOperator = Regex("\\A\\*")
    }

    fun tokenize(input: String): List<Token> {
        return recursivelyTokenize(input, ArrayList(), 1, 0)
            .filter { it !is WhitespacesToken }
    }

    private fun recursivelyTokenize(
        input: String,
        tokenList: MutableList<Token>,
        lineNumber: Int,
        linePosition: Int
    ): List<Token> {

        if (input.isEmpty()) {
            return returnTokenListWithEof(tokenList, lineNumber, linePosition)
        }

        val nextToken: Token = parseNextToken(input, lineNumber, linePosition)

        tokenList.add(nextToken)

        return recursivelyTokenize(
            input.substring(nextToken.length),
            tokenList,
            if (nextToken is NewLineToken) lineNumber + 1 else lineNumber,
            if (nextToken is NewLineToken) 0 else linePosition + nextToken.length
        )
    }

    private fun parseNextToken(input: String, lineNumber: Int, start: Int): Token {
        if (startsWithWhitespaces(input)) {
            return parseWhitespacesToken(input, lineNumber, start)
        }

        if (startsWithInteger(input)) {
            return parseIntToken(input, lineNumber, start)
        }

        if (nextIsNewLine(input)) {
            return NewLineToken(lineNumber, start)
        }

        if (nextIsBinarySumOperator(input)) {
            return BinarySumOperatorToken(lineNumber, start)
        }

        if (nextIsBinaryMultiplicationOperator(input)) {
            return BinaryMultiplicationOperatorToken(lineNumber, start)
        }

        return throwTokenizeException(input)
    }

    private fun nextIsNewLine(input: String) = newLine.containsMatchIn(input)

    private fun nextIsBinarySumOperator(input: String) = binarySumOperator.containsMatchIn(input)

    private fun nextIsBinaryMultiplicationOperator(input: String) = binaryMultiplicationOperator.containsMatchIn(input)

    private fun throwTokenizeException(input: String): Token {
        val displayableInput = if (input.length > MAX_DISPLAYABLE_INPUT_LENGTH) {
            input.substring(0, 10) + "..."
        } else {
            input
        }

        throw TokenizeException("Unrecognized input: '$displayableInput'")
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
