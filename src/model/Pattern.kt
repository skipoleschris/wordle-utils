package model

data class Pattern(
    val characters: String,
    val exclusions: Set<Char>
) {
    fun wordLength() = characters.length

    fun isIndexKnown(index: Int) = characters[index].isUpperCase()

    fun knownLetters(): Set<Pair<Int, Char>> =
        characters.foldIndexed(setOf()) { index, result, ch ->
            if (ch.isUpperCase()) result.plus(Pair(index, ch.lowercaseChar()))
            else result
        }

    fun misLocatedLetters(): Map<Char, Set<Int>> {
        val knownLetterIndexes = knownLetters().map { it.first }
        return characters.foldIndexed(mapOf()) { index, result, ch ->
            if (ch.isLowerCase())
                result.plus(Pair(ch, (0 until wordLength())
                    .filterNot { it == index }
                    .filterNot { knownLetterIndexes.contains(it) }
                    .toSet()))
            else result
        }
    }

    fun isEarlyAttempt() = exclusions.size <= 5

    fun pureExclusions(): Set<Char> {
        val nonExclusions = characters.lowercase().toSet()
        return exclusions.filterNot { nonExclusions.contains(it) }.toSet()
    }

    fun multipleExclusions(): Set<Pair<Char, Int>> =
        characters.filterNot { it == '-' }
            .lowercase()
            .groupBy { it }
            .map {
                Pair(it.key, it.value.size)
            }
            .filter { exclusions.contains(it.first) }
            .toSet()
}
