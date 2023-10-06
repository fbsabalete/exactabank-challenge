package com.exactaworks.exactabank.model

import com.exactaworks.exactabank.exception.InsufficientBalanceException
import jakarta.persistence.*
import java.math.BigDecimal

@Entity
class Account(
    @Column(name = "ID") @Id @GeneratedValue(strategy = GenerationType.IDENTITY) var id: Long? = null,
    @Column(name = "BALANCE") var balance: BigDecimal,
    @OneToMany(mappedBy = "account", fetch = FetchType.LAZY) var pixKeys : List<PixKey>
) {

    fun verifyBalance(amountToDecrement : BigDecimal) {
        if(balance.compareTo(amountToDecrement) < 0) {
            throw InsufficientBalanceException("Account does not have enough balance to execute transaction")
        }
    }
}