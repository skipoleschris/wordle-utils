package wordle.model

import org.springframework.http.HttpStatus
import java.time.LocalDateTime

data class ErrorModel(
    val status: HttpStatus,
    val timestamp: LocalDateTime = LocalDateTime.now(),
    val message: String
)
