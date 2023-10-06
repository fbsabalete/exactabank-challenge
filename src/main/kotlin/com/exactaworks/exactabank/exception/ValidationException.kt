package com.exactaworks.exactabank.exception

import org.springframework.http.HttpStatus

class ValidationException(message: String) : BaseException(message, HttpStatus.BAD_REQUEST) {
}