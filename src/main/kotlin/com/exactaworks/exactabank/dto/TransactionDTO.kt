package com.exactaworks.exactabank.dto

import com.exactaworks.exactabank.model.TransactionType
import com.fasterxml.jackson.annotation.JsonInclude
import java.io.Serializable
import java.math.BigDecimal
import java.time.Instant


@JsonInclude(JsonInclude.Include.NON_NULL)
data class TransactionDTO(
    val id: Long? = null,
    val transactionType: TransactionType? = null,
    val amount: BigDecimal? = null,
    val agencyNumber: Int? = null,
    val dateTime: Instant? = null,
    val operation: OperationType? = null
) : Serializable

enum class OperationType {
    CREDIT, DEBIT
}
