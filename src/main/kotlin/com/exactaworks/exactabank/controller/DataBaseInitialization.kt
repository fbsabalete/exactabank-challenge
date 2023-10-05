package com.exactaworks.exactabank.controller

import com.exactaworks.exactabank.model.Account
import com.exactaworks.exactabank.model.PixKey
import com.exactaworks.exactabank.model.PixKeyType
import com.exactaworks.exactabank.repository.AccountRepository
import com.exactaworks.exactabank.repository.PixKeyRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component
import java.math.BigDecimal

@Component
class DataBaseInitialization(
    private val accountRepository: AccountRepository,
    private val pixKeyRepository: PixKeyRepository
) : CommandLineRunner {



    override fun run(vararg args: String?) {
        val account1 = accountRepository.save(Account(1L, BigDecimal("100"), listOf()))
        val account2 = accountRepository.save(Account(2L, BigDecimal("100"), listOf()))
        pixKeyRepository.save(PixKey(1L, PixKeyType.CPF, "12312312312", account1))
        pixKeyRepository.save(PixKey(2L, PixKeyType.EMAIL, "email@email.com", account2))
    }
}