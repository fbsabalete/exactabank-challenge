package com.exactaworks.exactabank.exception

import org.springframework.http.HttpStatus

class NotFoundException(message: String) : BaseException(message, HttpStatus.NOT_FOUND) {
}