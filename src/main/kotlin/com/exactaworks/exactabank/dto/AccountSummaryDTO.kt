package com.exactaworks.exactabank.dto

import java.io.Serializable
import java.math.BigDecimal


data class AccountSummaryDTO(
    val balance: BigDecimal? = null,
    val transactions: List<TransactionDTO>
) : Serializable