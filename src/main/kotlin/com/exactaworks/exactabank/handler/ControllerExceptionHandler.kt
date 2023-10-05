package com.exactaworks.exactabank.handler

import com.exactaworks.exactabank.exception.BaseException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ControllerExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleMethodArgumentNotValidException(exception : MethodArgumentNotValidException) : ErrorResponse {
        var errors : MutableList<String> = mutableListOf<String>()
        exception.bindingResult.fieldErrors
            .forEach({errors.add(it.field + " " + it.defaultMessage)})
        exception.bindingResult.globalErrors
            .forEach({errors.add(it.defaultMessage!!)})
        return ErrorResponse(status = HttpStatus.BAD_REQUEST.value(), message = "Invalid request", errors = errors)
    }

    @ExceptionHandler(BaseException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleBaseException(exception : BaseException) : ResponseEntity<ErrorResponse> {
        exception.httpStatus

        return ResponseEntity.status(exception.httpStatus)
            .body(ErrorResponse(status = exception.httpStatus.value(), message = exception.message!!, errors = mutableListOf()))
    }

}