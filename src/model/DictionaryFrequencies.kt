package model

typealias Frequencies = List<Frequency>

data class DictionaryFrequencies(
    val letterFrequencies: Frequencies,
    val letterFrequenciesByPosition: List<Frequencies>
) {
    fun probabilityOfLetter(letter: Char): Double {
        val occurrenceCount = letterFrequencies.find { it.letter == letter }?.count?.toDouble() ?: 0.0
        val totalCount = letterFrequencies.sumOf { it.count }.toDouble()
        return occurrenceCount / totalCount
    }

    fun probabilityOfLetterAt(letter: Char, position: Int): Double {
        val index = position - 1
        val occurrenceCount = letterFrequenciesByPosition[index].find { it.letter == letter }?.count?.toDouble() ?: 0.0
        val totalCount = letterFrequenciesByPosition[index].sumOf { it.count }.toDouble()
        return occurrenceCount / totalCount
    }
}
