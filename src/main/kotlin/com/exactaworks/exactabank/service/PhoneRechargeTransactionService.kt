package com.exactaworks.exactabank.service

import com.exactaworks.exactabank.client.PhoneCreditApiClient
import com.exactaworks.exactabank.dto.PhoneCreditApiRequest
import com.exactaworks.exactabank.dto.TransactionRequest
import com.exactaworks.exactabank.model.Transaction
import com.exactaworks.exactabank.repository.AccountRepository
import com.exactaworks.exactabank.model.TransactionType
import com.exactaworks.exactabank.repository.TransactionRepository
import org.springframework.stereotype.Service

@Service
class PhoneRechargeTransactionService(
    private val accountService: AccountService,
    private val transactionRepository: TransactionRepository,
    private val phoneCreditApiClient: PhoneCreditApiClient
) : TransactionService {
    override fun executeTransaction(request: TransactionRequest) {
        val account = accountService.findById(request.accountId!!)
        accountService.decrementBalance(account = account, amount = request.amount!!)
        phoneCreditApiClient.rechargePhone(PhoneCreditApiRequest(request.phoneNumber!!))
        val transaction = Transaction(account = account, amount = request.amount!!, transactionType = getType())
        transactionRepository.save(transaction)
    }

    override fun getType(): TransactionType {
        return TransactionType.PHONE_RECHARGE
    }
}