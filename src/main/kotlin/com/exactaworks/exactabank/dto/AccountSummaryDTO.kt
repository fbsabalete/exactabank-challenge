package com.exactaworks.exactabank.dto

import com.exactaworks.exactabank.extensions.toRealString
import java.io.Serializable
import java.math.BigDecimal


data class AccountSummaryDTO(
    val balance: BigDecimal,
    val balanceFormatted: String? = balance.toRealString(),
    val transactions: List<TransactionDTO>
) : Serializable