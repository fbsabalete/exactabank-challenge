package com.exactaworks.exactabank.service

import com.exactaworks.exactabank.dto.TransactionRequest
import com.exactaworks.exactabank.model.Account
import com.exactaworks.exactabank.model.Transaction
import com.exactaworks.exactabank.model.TransactionType
import com.exactaworks.exactabank.repository.TransactionRepository
import org.springframework.stereotype.Service
import java.lang.IllegalArgumentException

@Service
class PixTransactionService(private val accountService: AccountService,
                            private val transactionRepository: TransactionRepository
) : TransactionService {
    override fun executeTransaction(request: TransactionRequest) {
        val targetAccount : Account =
            accountService.findByPixTypeAndKey(request.pixKeyType!!, request.pixKey!!)
        val sourceAccount = accountService.findById(request.accountId!!)
        if(targetAccount.id == sourceAccount.id) {
            throw IllegalArgumentException("Can't transfer to same account")
        }

        val transactionAmount = request.amount!!
        accountService.decrementBalance(sourceAccount, transactionAmount)
        accountService.incrementBalance(targetAccount, transactionAmount)

        transactionRepository
            .save(Transaction(account = sourceAccount, targetAccount = targetAccount, transactionType = getType(), amount = transactionAmount))
    }

    override fun getType(): TransactionType {
        return TransactionType.PIX
    }
}