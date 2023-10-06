package com.exactaworks.exactabank.service.strategy

import com.exactaworks.exactabank.dto.TransactionRequest
import com.exactaworks.exactabank.model.Account
import com.exactaworks.exactabank.model.Transaction
import com.exactaworks.exactabank.model.TransactionType
import com.exactaworks.exactabank.service.AccountService
import com.exactaworks.exactabank.service.TransactionService
import org.springframework.stereotype.Service
import java.lang.IllegalArgumentException

@Service
class PixTransactionStrategy(
    private val accountService: AccountService
) : TransactionStrategy {
    override fun executeTransaction(request: TransactionRequest): Transaction {
        val targetAccount : Account =
            accountService.findByPixTypeAndKey(request.pixKeyType!!, request.pixKey!!)
        val sourceAccount = accountService.findById(request.accountId!!)
        if(targetAccount.id == sourceAccount.id) {
            throw IllegalArgumentException("Can't transfer to same account")
        }

        val transactionAmount = request.amount!!
        accountService.decrementBalance(sourceAccount, transactionAmount)
        accountService.incrementBalance(targetAccount, transactionAmount)

        return Transaction(
            account = sourceAccount,
            targetAccount = targetAccount,
            transactionType = getType(),
            amount = transactionAmount
        )

    }

    override fun getType(): TransactionType {
        return TransactionType.PIX
    }
}