package cnogueira.sparky.exceptions


class ExpressionEvaluationException : RuntimeException()

class ParseException(msg: String) : RuntimeException(msg)

class TokenizeException(msg: String) : RuntimeException(msg)
