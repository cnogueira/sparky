package cnogueira.sparky.parser

abstract class Token(val lineNumber: Int, val start: Int, val length: Int, open val value: Any) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Token) return false

        if (lineNumber != other.lineNumber) return false
        if (start != other.start) return false
        if (length != other.length) return false
        if (value != other.value) return false

        return true
    }

    override fun hashCode(): Int {
        var result = lineNumber
        result = 31 * result + start
        result = 31 * result + length
        result = 31 * result + value.hashCode()
        return result
    }

    override fun toString(): String {
        return "Token(lineNumber=$lineNumber, start=$start, length=$length, value=$value)"
    }
}

class IntToken(lineNumber: Int, start: Int, length: Int, override val value: Int) :
    Token(lineNumber, start, length, value)

class WhitespacesToken(lineNumber: Int, start: Int, length: Int, override val value: String) :
    Token(lineNumber, start, length, value.map { '-' })

class NewLineToken(lineNumber: Int, start: Int) : Token(lineNumber, start, 1, "\\n")

class EofToken(lineNumber: Int, start: Int) : Token(lineNumber, start, 0, "\$")
