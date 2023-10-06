package com.exactaworks.exactabank.helper

import com.exactaworks.exactabank.model.Account
import com.exactaworks.exactabank.model.PixKey
import com.exactaworks.exactabank.model.PixKeyType
import com.exactaworks.exactabank.repository.AccountRepository
import com.exactaworks.exactabank.repository.PixKeyRepository
import org.springframework.stereotype.Component
import java.math.BigDecimal

@Component
class DataHelper(
    private val accountRepository: AccountRepository,
    private val pixKeyRepository: PixKeyRepository
)  {

    fun populate() {
        val account1 = accountRepository.save(Account(1L, BigDecimal("100"), listOf()))
        val account2 = accountRepository.save(Account(2L, BigDecimal("100"), listOf()))
        pixKeyRepository.save(PixKey(1L, PixKeyType.CPF, "12312312312", account1))
        pixKeyRepository.save(PixKey(2L, PixKeyType.EMAIL, "email@email.com", account2))
    }
}