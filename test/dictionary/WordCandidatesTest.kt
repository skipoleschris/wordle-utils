package dictionary

import model.Dictionary
import model.Frequency
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class WordCandidatesTest {

    @Test
    fun `should know if a word contains duplicate letters`() {
        assertTrue(hasDuplicateLetter("snows"))
        assertFalse(hasDuplicateLetter("spite"))
    }

    @Test
    fun `should generate all possible combinations from a set of letters`() {
        val combinations = combinations(listOf(
            listOf(Frequency('a', 1), Frequency('b', 1)),
            listOf(Frequency('c', 1), Frequency('d', 1)),
            listOf(Frequency('e', 1), Frequency('f', 1)),
        ))

        assertEquals(8, combinations.size)
        assertTrue(combinations.contains("ace"))
        assertTrue(combinations.contains("acf"))
        assertTrue(combinations.contains("ade"))
        assertTrue(combinations.contains("adf"))
        assertTrue(combinations.contains("bce"))
        assertTrue(combinations.contains("bcf"))
        assertTrue(combinations.contains("bde"))
        assertTrue(combinations.contains("bdf"))
    }

    @Test
    fun `should handle exclusions of duplicate letters`() {
        assertFalse(exclusions(true).invoke("snows"))
        assertTrue(exclusions(true).invoke("spite"))
    }

    @Test
    fun `should handle exclusions of no duplicate letters`() {
        assertTrue(exclusions(false).invoke("snows"))
        assertTrue(exclusions(false).invoke("spite"))
    }

    @Test
    fun `should generate an acceptable list of word candidates`() {
        val allWords = setOf("stove", "scuba", "smile", "smite", "snows")
        val allowedWords = setOf("stove", "scuba", "smile", "smite", "snows")
        val candidates = generateWordCandidates(
            Dictionary(allWords, allowedWords),
            analyseDictionary(allowedWords),
            5,
            3,
            true
        )

        assertEquals(2, candidates.size)
        assertTrue(candidates.contains("smile"))
        assertTrue(candidates.contains("smite"))
    }

    @Test
    fun `should generate an acceptable list of word candidates with defaults`() {
        val allWords = setOf("stove", "scuba", "smile", "smite", "snows")
        val allowedWords = setOf("stove", "scuba", "smile", "smite", "snows")
        val candidates = generateWordCandidates(
            Dictionary(allWords, allowedWords),
            analyseDictionary(allowedWords),
            5
        )

        assertEquals(5, candidates.size)
        assertTrue(candidates.contains("stove"))
        assertTrue(candidates.contains("scuba"))
        assertTrue(candidates.contains("smile"))
        assertTrue(candidates.contains("smite"))
        assertTrue(candidates.contains("snows"))
    }
}