package com.sooni.postapi.application.handler

import com.sooni.postapi.application.support.SunganError
import com.sooni.postapi.application.support.SunganException
import com.sooni.postapi.application.support.SunganResponse
import mu.KotlinLogging
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {
    private val logger = KotlinLogging.logger {}

    @ExceptionHandler(SunganException::class)
    fun sunganException(e: SunganException): ResponseEntity<SunganResponse<Unit>> {
        val error: SunganError = e.error
        logger.error { "SunganException: ${error.desc}" }
        return ResponseEntity
            .status(error.status)
            .body(SunganResponse(error.status, error.desc))
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun methodArgumentNotValidException(e: MethodArgumentNotValidException): ResponseEntity<SunganResponse<Unit>> {
        logger.error { "MethodArgumentNotValidException: " + e.message }
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(SunganResponse(HttpStatus.BAD_REQUEST, e.message))
    }

    @ExceptionHandler(RuntimeException::class)
    fun unknownException(e: Exception): ResponseEntity<SunganResponse<Unit>> {
        logger.error { "UnknownException: ${e.localizedMessage}" }
        return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(SunganResponse(HttpStatus.INTERNAL_SERVER_ERROR, SunganError.UNKNOWN_ERROR.desc))
    }
}