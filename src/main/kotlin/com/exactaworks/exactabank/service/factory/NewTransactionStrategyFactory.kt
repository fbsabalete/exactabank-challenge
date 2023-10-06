package com.exactaworks.exactabank.service.factory

import com.exactaworks.exactabank.model.TransactionType
import com.exactaworks.exactabank.service.strategy.TransactionStrategy
import org.springframework.stereotype.Component

@Component
class NewTransactionStrategyFactory(transactionStrategies: List<TransactionStrategy>) {

    lateinit var transactionServices : Map<TransactionType, TransactionStrategy>

    init {
        this.transactionServices = transactionStrategies.associateBy { it.getType() }
    }

    fun getStrategy(type: TransactionType): TransactionStrategy {
        return this.transactionServices.get(type) ?: throw IllegalArgumentException("Type " + type + " does not have a dedicated strategy")
    }
}