package wordle.model

data class SolutionWord(
    val word: String,
    val letterProbabilities: List<Double>
) {
    val averageProbability = letterProbabilities.average()

    override fun toString(): String =
        "$word: $averageProbability (${letterProbabilities.joinToString()})"

    companion object {

        fun createBuilder(pattern: Pattern, analysis: DictionaryFrequencies):
                    (String) -> SolutionWord =
            fun(word: String) = SolutionWord(
                word,
                word.toList().mapIndexed { index, letter ->
                    if (pattern.isIndexKnown(index)) 1.0
                    else analysis.probabilityOfLetterAt(letter, index + 1)
                }
            )
    }
}
