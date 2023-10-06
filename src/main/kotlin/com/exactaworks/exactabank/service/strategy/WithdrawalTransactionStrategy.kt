package com.exactaworks.exactabank.service.strategy

import com.exactaworks.exactabank.dto.TransactionRequest
import com.exactaworks.exactabank.model.Transaction
import com.exactaworks.exactabank.model.TransactionType
import com.exactaworks.exactabank.service.AccountService
import com.exactaworks.exactabank.service.TransactionService
import org.springframework.stereotype.Service

@Service
class WithdrawalTransactionStrategy(
    private val accountService: AccountService
) : TransactionStrategy {
    override fun executeTransaction(request: TransactionRequest): Transaction {
        val account = accountService.findById(request.accountId!!)
        accountService.decrementBalance(account = account, amount = request.amount!!)
        return Transaction(
            account = account,
            amount = request.amount!!,
            agencyNumber = request.agencyNumber,
            transactionType = getType()
        )
    }

    override fun getType(): TransactionType {
        return TransactionType.WITHDRAWAL
    }
}