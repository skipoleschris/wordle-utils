package wordle

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
open class WordleSolver

fun main(args: Array<String>) {
    runApplication<WordleSolver>(*args)
}



