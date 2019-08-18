package cnogueira.sparky.parser

import cnogueira.sparky.exceptions.ParseException
import cnogueira.sparky.grammar.BinaryMultExpression
import cnogueira.sparky.grammar.BinarySumExpression
import cnogueira.sparky.grammar.Expression
import cnogueira.sparky.grammar.LiteralExpression
import cnogueira.sparky.lexer.BinaryMultOperatorToken
import cnogueira.sparky.lexer.BinarySumOperatorToken
import cnogueira.sparky.lexer.IntToken
import cnogueira.sparky.lexer.Token

class Parser {
    fun parseExpression(tokens: List<Token>): Expression {
        if (tokens.isEmpty()) {
            throw ParseException("Unexpected end of expression")
        }

        val firstToken = tokens.first()

        if (firstToken !is IntToken) {
            throwParseExceptionForUnexpectedToken(firstToken)
        }

        val literalExpression = LiteralExpression((firstToken as IntToken).value)

        if (tokens.size == 1) {
            return literalExpression
        }

        if (tokens[1] is BinaryMultOperatorToken) {
            return parseMultExpression(literalExpression, tokens.drop(2))
        }

        if (tokens[1] is BinarySumOperatorToken) {
            return parseSumExpression(literalExpression, tokens.drop(2))
        }

        return throwParseExceptionForUnexpectedToken(tokens[1])
    }

    private fun parseSumExpression(leftExpr: Expression, tokens: List<Token>): Expression {
        return BinarySumExpression(leftExpr, parseExpression(tokens))
    }

    private fun parseMultExpression(leftExpr: Expression, tokens: List<Token>): Expression {
        return BinaryMultExpression(leftExpr, parseExpression(tokens))
    }

    private fun throwParseExceptionForUnexpectedToken(token: Token): Expression {
        val errorMetadata = "[${token.lineNumber}:${token.start}..${token.start + token.length}]: "

        throw ParseException(errorMetadata + "Unexpected token: '${token.value}'")
    }
}
