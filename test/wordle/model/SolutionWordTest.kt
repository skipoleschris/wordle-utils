package wordle.model

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import kotlin.test.assertEquals

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class SolutionWordTest {

    @Test
    fun `should calculate average probability for the word`() {
        val word = SolutionWord("test", listOf(0.5, 0.6, 0.4, 0.5))
        assertEquals(0.5, word.averageProbability())
    }

    @Test
    fun `should have a custom string representation`() {
        val word = SolutionWord("test", listOf(0.5, 0.6, 0.4, 0.5))
        assertEquals("test: 0.5 (0.5, 0.6, 0.4, 0.5)", word.toString())
    }

    @Test
    fun `should have a builder that generates solution words`() {
        val pattern = Pattern("S-et-", setOf('a', 'r'))
        val analysis = DictionaryFrequencies(
            listOf(
                Frequency('s', 10),
                Frequency('t', 15),
                Frequency('o', 20),
                Frequency('v', 5),
                Frequency('e', 25),
                Frequency('a', 25),
                Frequency('r', 20)
            ),
            listOf(
                listOf(Frequency('s', 10), Frequency('a', 25)),
                listOf(Frequency('t', 10), Frequency('r', 20)),
                listOf(Frequency('o', 10), Frequency('e', 15)),
                listOf(Frequency('v', 5), Frequency('o', 10)),
                listOf(Frequency('e', 10), Frequency('t', 5)),
            )
        )
        val builder = SolutionWord.createBuilder(pattern, analysis)
        assertEquals(
            SolutionWord("stove", listOf(
                1.0,
                10.0 / 30.0,
                10.0 / 25.0,
                5.0 / 15.0,
                10.0 / 15.0
            )),
            builder("stove")
        )
    }
}