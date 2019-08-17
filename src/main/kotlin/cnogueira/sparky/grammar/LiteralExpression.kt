package cnogueira.sparky.grammar

class LiteralExpression(private val value: Int) : Expression {
    override fun value(): Int {
        TODO("unimplemented")
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as LiteralExpression

        if (value != other.value) return false

        return true
    }

    override fun hashCode(): Int {
        return value
    }
}
