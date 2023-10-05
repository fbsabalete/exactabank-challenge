package com.exactaworks.exactabank.exception

import org.springframework.http.HttpStatus

open class BaseException(message: String, var httpStatus: HttpStatus) : RuntimeException(message) {
}