package com.exactaworks.exactabank.service.strategy

import com.exactaworks.exactabank.client.PhoneCreditApiClient
import com.exactaworks.exactabank.dto.PhoneCreditApiRequest
import com.exactaworks.exactabank.dto.TransactionRequest
import com.exactaworks.exactabank.model.Transaction
import com.exactaworks.exactabank.model.TransactionType
import com.exactaworks.exactabank.service.AccountService
import org.springframework.stereotype.Service

@Service
class PhoneRechargeTransactionStrategy(
    private val accountService: AccountService,
    private val phoneCreditApiClient: PhoneCreditApiClient
) : TransactionStrategy {
    override fun executeTransaction(request: TransactionRequest): Transaction {
        val account = accountService.findById(request.accountId!!)
        accountService.decrementBalance(account = account, amount = request.amount!!)
        phoneCreditApiClient.rechargePhone(PhoneCreditApiRequest(request.phoneNumber!!))
        return Transaction(
            account = account,
            amount = request.amount!!,
            transactionType = getType()
        )
    }

    override fun getType(): TransactionType {
        return TransactionType.PHONE_RECHARGE
    }
}