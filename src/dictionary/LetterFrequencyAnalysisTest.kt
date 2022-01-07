package dictionary

import model.DictionaryFrequencies
import model.Frequencies
import model.Frequency

fun analyseDictionary(words: Set<String>): DictionaryFrequencies =
    DictionaryFrequencies(
        letterFrequenciesForDictionary(words),
        letterFrequenciesByPositionForDictionary(words, words.first().length)
    )

internal fun letterFrequenciesForDictionary(words: Set<String>): Frequencies =
    words
        .flatMap(::analyseWord)
        .groupBy { it.letter }
        .map { entry ->
            Frequency(entry.key, entry.value.sumOf { it.count })
        }
        .sortedBy { it.count }
        .reversed()


internal fun letterFrequenciesByPositionForDictionary(words: Set<String>, wordLength: Int): List<Frequencies> =
    (0 until wordLength).map { index ->
        words
            .asSequence()
            .map { it[index] }
            .groupBy { it }
            .map {
                Frequency(it.key, it.value.size)
            }
            .sortedBy { it.count }
            .reversed()
    }

internal fun analyseWord(word: String): List<Frequency> =
    word.toList()
        .groupBy { it }
        .mapValues { it.value.size }
        .map {
            Frequency(it.key, it.value)
        }
