package model

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PatternTest {

    @Test
    fun `should derive the work length from the characters pattern`() {
        val pattern = Pattern("-S-t-", setOf())

        assertEquals("-S-t-", pattern.characters)
        assertEquals(5, pattern.wordLength())
    }

    @Test
    fun `should report whether the character as a particular index is known`() {
        val pattern = Pattern("-S-t-", setOf())

        assertFalse(pattern.isIndexKnown(0))
        assertTrue(pattern.isIndexKnown(1))
        assertFalse(pattern.isIndexKnown(2))
        assertFalse(pattern.isIndexKnown(3))
        assertFalse(pattern.isIndexKnown(4))
    }

    @Test
    fun `should extract the known letters and their indexes from the pattern`() {
        val pattern = Pattern("-S-tE", setOf())
        val knownLetters = pattern.knownLetters()

        assertEquals(2, knownLetters.size)
        assertTrue(knownLetters.contains(Pair(1, 's')))
        assertTrue(knownLetters.contains(Pair(4, 'e')))
    }

    @Test
    fun `should extract the potential locations of mis-located characters from the pattern`() {
        val pattern = Pattern("-S-te", setOf())
        val misLocatedLetters = pattern.misLocatedLetters()

        assertEquals(2, misLocatedLetters.size)

        val letterT = misLocatedLetters.getValue('t')
        assertEquals(3, letterT.size)
        assertTrue(letterT.contains(0))
        assertTrue(letterT.contains(2))
        assertTrue(letterT.contains(4))

        val letterE = misLocatedLetters.getValue('e')
        assertEquals(3, letterE.size)
        assertTrue(letterE.contains(0))
        assertTrue(letterE.contains(2))
        assertTrue(letterE.contains(3))
    }

    @Test
    fun `should determine whether the guess is an early attempt or not`() {
        assertTrue(Pattern("-----", setOf('a', 'b', 'c', 'd', 'e')).isEarlyAttempt())
        assertFalse(Pattern("-----", setOf('a', 'b', 'c', 'd', 'e', 'f')).isEarlyAttempt())
    }

    @Test
    fun `should report the pure exclusion letters that are not in the pattern at all`() {
        val pattern = Pattern("-S-te", setOf('a', 'r', 's'))
        val pureExclusions = pattern.pureExclusions()

        assertEquals(2, pureExclusions.size)
        assertTrue(pureExclusions.contains('a'))
        assertTrue(pureExclusions.contains('r'))
    }

    @Test
    fun `should report the multiple exclusion letters that can appear in the pattern a number of times`() {
        val pattern = Pattern("-S-te", setOf('a', 'r', 's'))
        val multipleExclusions = pattern.multipleExclusions()

        assertEquals(1, multipleExclusions.size)
        assertTrue(multipleExclusions.contains(Pair('s', 1)))
    }
}