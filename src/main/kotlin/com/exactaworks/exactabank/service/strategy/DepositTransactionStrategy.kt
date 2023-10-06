package com.exactaworks.exactabank.service.strategy

import com.exactaworks.exactabank.dto.TransactionRequest
import com.exactaworks.exactabank.model.Transaction
import com.exactaworks.exactabank.model.TransactionType
import com.exactaworks.exactabank.service.AccountService
import com.exactaworks.exactabank.service.TransactionService
import org.springframework.stereotype.Service

@Service
class DepositTransactionStrategy(
    private val accountService: AccountService,
) : TransactionStrategy {
    override fun executeTransaction(request: TransactionRequest): Transaction {
        val account = accountService.findById(request.accountId!!)
        accountService.incrementBalance(account = account, amount = request.amount!!)
        return Transaction(
            account = account,
            amount = request.amount!!,
            transactionType = getType(),
            agencyNumber = request.agencyNumber
        )
    }

    override fun getType(): TransactionType {
        return TransactionType.DEPOSIT
    }
}