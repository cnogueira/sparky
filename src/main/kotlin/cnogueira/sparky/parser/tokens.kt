package cnogueira.sparky.parser

interface Token

data class IntToken(val value: Int) : Token

class NewLineToken : Token {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        return true
    }

    override fun hashCode(): Int {
        return javaClass.hashCode()
    }
}
