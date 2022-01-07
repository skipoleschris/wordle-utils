import dictionary.analyseDictionary
import dictionary.filterDictionaryAgainstPattern
import dictionary.generateWordCandidates
import model.DictionaryFrequencies
import model.Pattern
import model.SolutionWord
import kotlin.system.exitProcess

fun main(args: Array<String>) {
    val pattern = parseArgsToPattern(args)
    val (candidates, analysis) = produceCandidatesAndAnalysis(pattern)
    orderMostProbableCandidates(pattern, analysis, candidates).forEach(::println)
}

private fun produceCandidatesAndAnalysis(pattern: Pattern): Pair<Set<String>, DictionaryFrequencies> {
    val dictionary = filterDictionaryAgainstPattern(pattern)
    val analysisAllowed = analyseDictionary(dictionary.allowedWords)
    return Pair(
       generateWordCandidates(dictionary, analysisAllowed, pattern.wordLength(), 5, pattern.isEarlyAttempt()),
       analyseDictionary(dictionary.allWords)
   )
}

private fun orderMostProbableCandidates(
    pattern: Pattern,
    analysis: DictionaryFrequencies,
    candidates: Set<String>
): List<SolutionWord> {
    val builder = SolutionWord.createBuilder(pattern, analysis)
    return candidates.map(builder).sortedBy { it.averageProbability() }.reversed()
}

private fun parseArgsToPattern(args: Array<String>): Pattern {
    if (args.isEmpty() || args.size > 2) {
        println("ERROR: invalid number of commandline options")
        showHelpAndExit()
    }

    if (!Regex("""[\-a-zA-Z]{5}""").matches(args[0])) {
        println("ERROR: specified word pattern '${args[0]}' is not valid")
        showHelpAndExit()
    }

    if (args.size == 2 && !Regex("""[a-z]*""").matches(args[1])) {
        println("ERROR: specified exclusion list '${args[1]}' is not valid")
        showHelpAndExit()
    }

    val exclusions =
        if (args.size == 2) args[1].toSet()
        else setOf()

    return Pattern(args[0], exclusions)
}

private fun showHelpAndExit() {
    println("Usage: java -jar wordle-solver.jar <pattern> [exclusions]")
    println("Where:")
    println("  pattern:     the current pattern")
    println("                   '-' : unknown character")
    println("                   lower case : present character, wrong position")
    println("                   upper case : present character, correct position")
    println("  exclusions:  the characters that have been tried and not matched")
    exitProcess(1)
}
