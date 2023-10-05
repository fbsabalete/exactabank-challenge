package com.exactaworks.exactabank.service

import com.exactaworks.exactabank.dto.TransactionRequest
import com.exactaworks.exactabank.model.TransactionType
import org.springframework.transaction.annotation.Transactional

interface TransactionService {
    @Transactional
    fun executeTransaction(request: TransactionRequest)
    fun getType() : TransactionType
}