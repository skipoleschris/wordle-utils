package dictionary

import model.Dictionary
import model.DictionaryFrequencies
import model.Frequency

fun generateWordCandidates(
    dictionary: Dictionary,
    analysis: DictionaryFrequencies,
    wordLength: Int,
    depth: Int = 5,
    excludeDuplicateLetters: Boolean = false
): Set<String> =
    combinations((0 until wordLength).map {
        analysis.letterFrequenciesByPosition[it]
            .take(depth)
    })
    .filter { dictionary.allowedWords.contains(it) }
    .filter(exclusions(excludeDuplicateLetters))
        .toSet()

internal fun combinations(letterOptions: List<List<Frequency>>, word: String = ""): List<String> =
    if (letterOptions.isEmpty()) listOf(word)
    else letterOptions.first().flatMap { option ->
        combinations(letterOptions.drop(1), word.plus(option.letter))
    }

internal fun exclusions(excludeDuplicateLetters: Boolean): (String) -> Boolean =
    fun (word: String): Boolean =
        if (excludeDuplicateLetters) !hasDuplicateLetter(word)
        else true

internal fun hasDuplicateLetter(word: String): Boolean =
    word.toList()
        .groupBy { it }
        .map { it.value.size }
        .any { it > 1 }