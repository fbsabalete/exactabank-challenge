package com.exactaworks.exactabank.service.strategy

import com.exactaworks.exactabank.dto.TransactionRequest
import com.exactaworks.exactabank.model.Transaction
import com.exactaworks.exactabank.model.TransactionType
import org.springframework.transaction.annotation.Transactional

interface TransactionStrategy {
    fun executeTransaction(request: TransactionRequest) : Transaction
    fun getType() : TransactionType
}