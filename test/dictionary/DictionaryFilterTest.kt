package dictionary

import model.Pattern
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DictionaryFilterTest {

    @Test
    fun `should know if a word contains known letters from a pattern`() {
        val f = containsKnownLetters(Pattern("S-te-", setOf('a', 'r', 's')))
        assertTrue(f("stove"))
        assertFalse(f("quiet"))
    }

    @Test
    fun `should know if a word contains mis-located latters from a pattern`() {
        val f = containsMisLocatedLetters(Pattern("S-te-", setOf('a', 'r', 's')))
        assertTrue(f("stove"))
        assertFalse(f("snogs"))
    }

    @Test
    fun `should know if a word contains pure exclusions`() {
        val f = doesNotContainPureExclusions(Pattern("S-te-", setOf('a', 'r', 's')))
        assertTrue(f("stove"))
        assertFalse(f("cream"))
    }

    @Test
    fun `should know if a work contains multiple exclusions`() {
        val f = doesNotContainMultipleExclusions(Pattern("S-et-", setOf('a', 'r', 's')))
        assertTrue(f("stove"))
        assertFalse(f("sites"))
    }

    @Test
    fun `should load and filter dictionary for all words containing known letters`() {
        val dictionary = filterDictionaryAgainstPattern(
            Pattern("Si---", setOf('a')),
            "dictionary/test_words.txt"
        )

        assertEquals(4, dictionary.allWords.size)
        assertTrue(dictionary.allWords.contains("smile"))
        assertTrue(dictionary.allWords.contains("spite"))
        assertTrue(dictionary.allWords.contains("stove"))
        assertTrue(dictionary.allWords.contains("scuba"))
    }

    @Test
    fun `should load and filter dictionary for all words based on the full pattern`() {
        val dictionary = filterDictionaryAgainstPattern(
            Pattern("Si---", setOf('a')),
            "dictionary/test_words.txt"
        )

        assertEquals(2, dictionary.allowedWords.size)
        assertTrue(dictionary.allowedWords.contains("smile"))
        assertTrue(dictionary.allowedWords.contains("spite"))
    }

    @Test
    fun `should load and filter actual dictionary based on the full pattern`() {
        val dictionary = filterDictionaryAgainstPattern(
            Pattern("SC-ab", setOf('r', 't'))
        )

        assertEquals(126, dictionary.allWords.size)
        assertEquals(2, dictionary.allowedWords.size)
        assertTrue(dictionary.allowedWords.contains("scuba"))
        assertTrue(dictionary.allowedWords.contains("scabs"))
    }
}