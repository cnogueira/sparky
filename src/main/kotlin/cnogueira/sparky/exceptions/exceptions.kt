package cnogueira.sparky.exceptions


class ExpressionEvaluationException : RuntimeException()

class ParseException(msg: String) : RuntimeException(msg)
