package com.exactaworks.exactabank.service

import com.exactaworks.exactabank.exception.NotFoundException
import com.exactaworks.exactabank.model.Account
import com.exactaworks.exactabank.model.PixKeyType
import com.exactaworks.exactabank.repository.AccountRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.math.BigDecimal

@Service
class AccountService(private val accountRepository: AccountRepository) {

    fun findById(id: Long) : Account {
        return accountRepository.findByIdOrNull(id)
            ?: throw NotFoundException("Source account not found")
    }

    fun findByPixTypeAndKey(keyType: PixKeyType, key: String): Account {
        return accountRepository.findByPixKeys_KeyTypeAndPixKeys_Key(keyType, key)
            ?: throw NotFoundException("Account with informed Pix key does not exist")
    }

    fun decrementBalance(account: Account, amount: BigDecimal) {
        account.verifyBalance(amount)
        updateBalance(account, account.balance.minus(amount))
    }

    fun incrementBalance(account: Account, amount: BigDecimal) {
        updateBalance(account, account.balance.plus(amount))
    }

    private fun updateBalance(account: Account, newBalance : BigDecimal) {
        accountRepository.updateBalanceById(id = account.id, balance = newBalance)
    }

}