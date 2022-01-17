package wordle.model

data class Frequency(
    val letter: Char,
    val count: Int
) {
    override fun toString(): String = "$letter-$count"
}
