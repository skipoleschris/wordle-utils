package wordle.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import wordle.model.DictionaryFrequencies
import wordle.model.Pattern
import wordle.model.SolutionWord
import wordle.service.DictionaryAnalyser
import wordle.service.DictionaryFilter
import wordle.service.WordCandidates
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Pattern as VPattern

@RestController
@RequestMapping("/solve")
@Validated
open class WordleController(
    private val dictionaryFilter: DictionaryFilter,
    private val analyser: DictionaryAnalyser,
    private val wordCandidates: WordCandidates
) {

    @GetMapping("")
    open fun solve(
        @RequestParam(value = "pattern")
        @NotBlank(message ="pattern must be supplied")
        @VPattern(regexp = """[\-a-zA-Z]{5}""", message = "pattern must be valid") patternArg: String,
        @RequestParam(value = "exclusions", required = false, defaultValue = "")
        @VPattern(regexp = """[a-z]*""", message = "exclusions must be a list of lowercase characters") exclusionsArg: String
    ): ResponseEntity<List<SolutionWord>> {
        val pattern = Pattern(patternArg, exclusionsArg.toSet())
        val (candidates, analysis) = produceCandidatesAndAnalysis(pattern)
        val solutions = orderMostProbableCandidates(pattern, analysis, candidates)

        return ResponseEntity(solutions, HttpStatus.OK)
    }

    private fun produceCandidatesAndAnalysis(pattern: Pattern): Pair<Set<String>, DictionaryFrequencies> {
        val dictionary = dictionaryFilter.againstPattern(pattern)
        val analysisAllowed = analyser.analyseDictionary(dictionary.allowedWords)
        return Pair(
            wordCandidates.generate(dictionary, analysisAllowed, pattern.wordLength(), 5, pattern.isEarlyAttempt()),
            analyser.analyseDictionary(dictionary.allWords)
        )
    }

    private fun orderMostProbableCandidates(
        pattern: Pattern,
        analysis: DictionaryFrequencies,
        candidates: Set<String>
    ): List<SolutionWord> {
        val builder = SolutionWord.createBuilder(pattern, analysis)
        return candidates.map(builder).sortedBy { it.averageProbability }.reversed()
    }
}