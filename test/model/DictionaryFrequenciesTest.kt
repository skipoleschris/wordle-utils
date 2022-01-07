package model

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import kotlin.test.assertEquals

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DictionaryFrequenciesTest {

    private val analysis = DictionaryFrequencies(
        listOf(
            Frequency('a', 5),
            Frequency('b', 2),
            Frequency('c', 3),
            Frequency('d', 4)
        ),
        listOf(
            listOf(Frequency('a', 2), Frequency('b', 2), Frequency('d', 4)),
            listOf(Frequency('a', 3), Frequency('c', 3))
        )
    )

    @Test
    fun `should calculate probability of a letter occurring in a word`() {
        assertEquals((5.0 / 14.0), analysis.probabilityOfLetter('a'))
    }

    @Test
    fun `should calculate probability of a letter occurring at a given position in a word`() {
        assertEquals((3.0 / 6.0), analysis.probabilityOfLetterAt('a', 2))
    }
}