package wordle.service

import org.springframework.stereotype.Component
import wordle.model.Dictionary
import wordle.model.Pattern
import java.nio.file.Files
import java.nio.file.Path

@Component
class DictionaryFilter {

    fun againstPattern(pattern: Pattern, source: String = "dictionary/all_words.txt"): Dictionary {
        val allWords = Files.readAllLines(Path.of(source))
            .filter { it.length == pattern.wordLength() }
            .map { it.lowercase() }
            .filter(containsKnownLetters(pattern))
        return Dictionary(
            allWords.toSet(),
            allWords.filter(doesNotContainPureExclusions(pattern))
                .filter(doesNotContainMultipleExclusions(pattern))
                .filter(containsMisLocatedLetters(pattern))
                .toSet()
        )
    }

    companion object {
        internal fun containsKnownLetters(pattern: Pattern): (String) -> Boolean =
            fun (word: String) = pattern.knownLetters().all {
                word[it.first] == it.second
            }

        internal fun containsMisLocatedLetters(pattern: Pattern): (String) -> Boolean =
            fun (word: String) =
                pattern.misLocatedLetters().all { entry ->
                    entry.value.any {
                        word[it] == entry.key
                    }
                }

        internal fun doesNotContainPureExclusions(pattern: Pattern): (String) -> Boolean =
            fun (word: String): Boolean =
                pattern.pureExclusions().none { word.contains(it) }

        internal fun doesNotContainMultipleExclusions(pattern: Pattern): (String) -> Boolean =
            fun (word: String): Boolean {
                val letterFrequencies = word.toList()
                    .groupBy { it }
                    .map {
                        Pair(it.key, it.value.size)
                    }
                val multipleExclusions = pattern.multipleExclusions()

                return multipleExclusions.none { (exclusionChar, allowed) ->
                    (letterFrequencies.find { it.first == exclusionChar }?.second ?: 0) > allowed
                }
            }
    }
}
