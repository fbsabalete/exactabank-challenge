package com.exactaworks.exactabank.service.strategy

import com.exactaworks.exactabank.dto.TransactionRequest
import com.exactaworks.exactabank.model.Transaction
import com.exactaworks.exactabank.model.TransactionType

interface TransactionStrategy {
    fun executeTransaction(request: TransactionRequest) : Transaction
    fun getType() : TransactionType
}