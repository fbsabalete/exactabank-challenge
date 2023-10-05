package com.exactaworks.exactabank.exception

import org.springframework.http.HttpStatus

class InsufficientBalanceException(message: String) : BaseException(message, HttpStatus.BAD_REQUEST) {
}