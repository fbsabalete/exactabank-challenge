package com.exactaworks.exactabank.handler

import java.time.ZonedDateTime

class ErrorResponse(
    var timestamp: ZonedDateTime = ZonedDateTime.now(),
    var status: Int,
    var message: String,
    val errors: MutableList<String>?
) {

}