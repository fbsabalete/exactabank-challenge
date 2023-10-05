package com.exactaworks.exactabank.controller

import com.exactaworks.exactabank.dto.TransactionRequest
import com.exactaworks.exactabank.service.factory.TransactionServiceFactory
import jakarta.validation.Valid
import org.jetbrains.annotations.NotNull
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/transactions")
class TransactionController(
    private val transactionServiceFactory: TransactionServiceFactory
) {

    @PostMapping
    fun createTransaction(@RequestBody @Valid @NotNull request : TransactionRequest) {
        transactionServiceFactory.createService(request.transactionType!!).executeTransaction(request);
    }

}

