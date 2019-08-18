package cnogueira.sparky.grammar

class BinaryMultExpression(val left: Expression, val right: Expression) : Expression {
    override fun value(): Int {
        TODO("unimplemented")
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is BinaryMultExpression) return false

        if (left != other.left) return false
        if (right != other.right) return false

        return true
    }

    override fun hashCode(): Int {
        var result = left.hashCode()
        result = 31 * result + right.hashCode()
        return result
    }
}
