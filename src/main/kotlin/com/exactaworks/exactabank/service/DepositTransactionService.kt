package com.exactaworks.exactabank.service

import com.exactaworks.exactabank.dto.TransactionRequest
import com.exactaworks.exactabank.model.Transaction
import com.exactaworks.exactabank.model.TransactionType
import com.exactaworks.exactabank.repository.TransactionRepository
import org.springframework.stereotype.Service

@Service
class DepositTransactionService(
    private val accountService: AccountService,
    private val transactionRepository: TransactionRepository
) : TransactionService {
    override fun executeTransaction(request: TransactionRequest) {
        val account = accountService.findById(request.accountId!!)
        accountService.incrementBalance(account = account, amount = request.amount!!)
        val transaction = Transaction(account = account, amount = request.amount!!, transactionType = getType(), agencyNumber = request.agencyNumber)
        transactionRepository.save(transaction)
    }

    override fun getType(): TransactionType {
        return TransactionType.DEPOSIT
    }
}