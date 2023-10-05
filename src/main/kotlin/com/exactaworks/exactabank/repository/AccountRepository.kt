package com.exactaworks.exactabank.repository;

import com.exactaworks.exactabank.model.Account
import com.exactaworks.exactabank.model.PixKeyType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal

interface AccountRepository : JpaRepository<Account, Long> {


    fun findByPixKeys_KeyTypeAndPixKeys_Key(keyType: PixKeyType, key: String): Account?


    @Transactional
    @Modifying
    @Query("update Account a set a.balance = :balance where a.id = :id")
    fun updateBalanceById(@Param("balance") balance: BigDecimal, @Param("id") id: Long): Int
}