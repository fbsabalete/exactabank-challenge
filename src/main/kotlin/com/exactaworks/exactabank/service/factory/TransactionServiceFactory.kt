package com.exactaworks.exactabank.service.factory

import com.exactaworks.exactabank.model.TransactionType
import com.exactaworks.exactabank.service.TransactionService
import org.springframework.stereotype.Component
import java.lang.IllegalArgumentException
import java.util.stream.Collectors

@Component
class TransactionServiceFactory(transactionServices: List<TransactionService>) {

    lateinit var transactionServices : Map<TransactionType, TransactionService>

    init {
        this.transactionServices = transactionServices.associateBy { it.getType() }
    }

    fun createService(type: TransactionType): TransactionService {
        return this.transactionServices.get(type) ?: throw IllegalArgumentException("Type " + type + " does not have a dedicated service")
    }
}