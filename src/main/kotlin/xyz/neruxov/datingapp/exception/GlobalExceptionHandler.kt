package xyz.neruxov.datingapp.exception

import org.springframework.core.annotation.Order
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import xyz.neruxov.datingapp.exception.type.StatusCodeException
import xyz.neruxov.datingapp.exception.type.UnauthorizedException

/**
 * @author <a href="https://github.com/Neruxov">Neruxov</a>
 */
@Order(1000)
@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(UnauthorizedException::class)
    fun unauthorizedException(ex: UnauthorizedException): ResponseEntity<*> {
        return ResponseEntity.status(401).body(ex.message)
    }

    @ExceptionHandler(StatusCodeException::class)
    fun handleStatusCodeException(e: StatusCodeException): ResponseEntity<Any> {
        return ResponseEntity.status(e.statusCode).body(e.toMap())
    }

}