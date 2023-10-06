package com.exactaworks.exactabank.service

import com.exactaworks.exactabank.dto.TransactionDTO
import java.io.Serializable
import java.math.BigDecimal


data class AccountSummaryDTO(
    val balance: BigDecimal? = null,
    val transactions: List<TransactionDTO>
) : Serializable