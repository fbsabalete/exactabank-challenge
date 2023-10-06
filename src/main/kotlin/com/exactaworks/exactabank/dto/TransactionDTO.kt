package com.exactaworks.exactabank.dto

import com.exactaworks.exactabank.extensions.toRealString
import com.exactaworks.exactabank.model.TransactionType
import com.fasterxml.jackson.annotation.JsonInclude
import java.io.Serializable
import java.math.BigDecimal
import java.time.LocalDateTime


@JsonInclude(JsonInclude.Include.NON_NULL)
data class TransactionDTO(
    val id: Long,
    val transactionType: TransactionType,
    val amount: BigDecimal,
    val amountFormatted: String = amount.toRealString(),
    val agencyNumber: Int? = null,
    val dateTime: LocalDateTime,
    val operation: OperationType
) : Serializable

enum class OperationType {
    CREDIT, DEBIT
}
