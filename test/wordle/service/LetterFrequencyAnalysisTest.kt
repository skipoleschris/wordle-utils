package wordle.service

import wordle.model.Frequency
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import wordle.service.DictionaryAnalyser.Companion.analyseWord
import wordle.service.DictionaryAnalyser.Companion.letterFrequenciesByPositionForDictionary
import wordle.service.DictionaryAnalyser.Companion.letterFrequenciesForDictionary
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class LetterFrequencyAnalysisTest {

    private val words = setOf(
        "stove",
        "fired",
        "scuba",
        "ninja",
        "party",
        "divot",
        "queen",
        "smile",
        "spite",
        "tough"
    )

    @Test
    fun `should determine letter frequencies in a word`() {
        val analysis = analyseWord("snows")

        assertEquals(4, analysis.size)
        assertTrue(analysis.contains(Frequency('s', 2)))
        assertTrue(analysis.contains(Frequency('n', 1)))
        assertTrue(analysis.contains(Frequency('o', 1)))
        assertTrue(analysis.contains(Frequency('w', 1)))
    }

    @Test
    fun `should analyse letter frequencies across a set of words`() {
        val analysis = letterFrequenciesForDictionary(words)

        assertEquals(22, analysis.size)
        assertTrue(analysis.contains(Frequency('a', 3)))
        assertTrue(analysis.contains(Frequency('b', 1)))
        assertTrue(analysis.contains(Frequency('c', 1)))
        assertTrue(analysis.contains(Frequency('d', 2)))
        assertTrue(analysis.contains(Frequency('e', 6)))
        assertTrue(analysis.contains(Frequency('f', 1)))
        assertTrue(analysis.contains(Frequency('g', 1)))
        assertTrue(analysis.contains(Frequency('h', 1)))
        assertTrue(analysis.contains(Frequency('i', 5)))
        assertTrue(analysis.contains(Frequency('j', 1)))
        //assertTrue(analysis.contains(Frequency('k', 0)))
        assertTrue(analysis.contains(Frequency('l', 1)))
        assertTrue(analysis.contains(Frequency('m', 1)))
        assertTrue(analysis.contains(Frequency('n', 3)))
        assertTrue(analysis.contains(Frequency('o', 3)))
        assertTrue(analysis.contains(Frequency('p', 2)))
        assertTrue(analysis.contains(Frequency('q', 1)))
        assertTrue(analysis.contains(Frequency('r', 2)))
        assertTrue(analysis.contains(Frequency('s', 4)))
        assertTrue(analysis.contains(Frequency('t', 5)))
        assertTrue(analysis.contains(Frequency('u', 3)))
        assertTrue(analysis.contains(Frequency('v', 2)))
        //assertTrue(analysis.contains(Frequency('w', 0)))
        //assertTrue(analysis.contains(Frequency('x', 0)))
        assertTrue(analysis.contains(Frequency('y', 1)))
        //assertTrue(analysis.contains(Frequency('z', 0)))
    }

    @Test
    fun `should analyse letter frequencies by position for a set of words`() {
        val analysis = letterFrequenciesByPositionForDictionary(words, 5)

        assertEquals(5, analysis.size)

        val letter1 = analysis[0]
        assertEquals(7, letter1.size)
        assertTrue(letter1.contains(Frequency('s', 4)))
        assertTrue(letter1.contains(Frequency('f', 1)))
        assertTrue(letter1.contains(Frequency('n', 1)))
        assertTrue(letter1.contains(Frequency('p', 1)))
        assertTrue(letter1.contains(Frequency('d', 1)))
        assertTrue(letter1.contains(Frequency('q', 1)))
        assertTrue(letter1.contains(Frequency('t', 1)))

        val letter2 = analysis[1]
        assertEquals(8, letter2.size)
        assertTrue(letter2.contains(Frequency('t', 1)))
        assertTrue(letter2.contains(Frequency('i', 3)))
        assertTrue(letter2.contains(Frequency('c', 1)))
        assertTrue(letter2.contains(Frequency('a', 1)))
        assertTrue(letter2.contains(Frequency('u', 1)))
        assertTrue(letter2.contains(Frequency('m', 1)))
        assertTrue(letter2.contains(Frequency('p', 1)))
        assertTrue(letter2.contains(Frequency('o', 1)))

        val letter3 = analysis[2]
        assertEquals(7, letter3.size)
        assertTrue(letter3.contains(Frequency('o', 1)))
        assertTrue(letter3.contains(Frequency('r', 2)))
        assertTrue(letter3.contains(Frequency('u', 2)))
        assertTrue(letter3.contains(Frequency('n', 1)))
        assertTrue(letter3.contains(Frequency('v', 1)))
        assertTrue(letter3.contains(Frequency('e', 1)))
        assertTrue(letter3.contains(Frequency('i', 2)))

        val letter4 = analysis[3]
        assertEquals(8, letter4.size)
        assertTrue(letter4.contains(Frequency('v', 1)))
        assertTrue(letter4.contains(Frequency('e', 2)))
        assertTrue(letter4.contains(Frequency('b', 1)))
        assertTrue(letter4.contains(Frequency('j', 1)))
        assertTrue(letter4.contains(Frequency('t', 2)))
        assertTrue(letter4.contains(Frequency('o', 1)))
        assertTrue(letter4.contains(Frequency('l', 1)))
        assertTrue(letter4.contains(Frequency('g', 1)))

        val letter5 = analysis[4]
        assertEquals(7, letter5.size)
        assertTrue(letter5.contains(Frequency('e', 3)))
        assertTrue(letter5.contains(Frequency('d', 1)))
        assertTrue(letter5.contains(Frequency('a', 2)))
        assertTrue(letter5.contains(Frequency('y', 1)))
        assertTrue(letter5.contains(Frequency('t', 1)))
        assertTrue(letter5.contains(Frequency('n', 1)))
        assertTrue(letter5.contains(Frequency('h', 1)))
    }

    @Test
    fun `should produce a full analysis for a list of words`() {
        val analysis = DictionaryAnalyser().analyseDictionary(words)
        assertEquals(22, analysis.letterFrequencies.size)
        assertEquals(5, analysis.letterFrequenciesByPosition.size)
    }

    @Test
    fun `should have a custom frequency string`() {
        assertEquals("a-5", Frequency('a', 5).toString())
    }
}