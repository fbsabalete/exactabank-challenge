package com.exactaworks.exactabank.dto

import com.exactaworks.exactabank.model.PixKeyType
import com.exactaworks.exactabank.model.TransactionType
import com.exactaworks.exactabank.validation.annotation.ValidTransactionType
import jakarta.validation.constraints.NotNull
import java.math.BigDecimal

@ValidTransactionType.List([
    ValidTransactionType(
        type = TransactionType.PIX,
        requiredFields = ["pixKeyType", "pixKey"]
    ),
    ValidTransactionType(
        type = TransactionType.DEPOSIT,
        requiredFields = ["agencyNumber"]
    ),
    ValidTransactionType(
        type = TransactionType.WITHDRAWAL,
        requiredFields = ["agencyNumber"]
    ),
    ValidTransactionType(
        type = TransactionType.PHONE_RECHARGE,
        requiredFields = ["phoneNumber"]
    )
])
data class TransactionRequest(
    @field:NotNull
    var accountId: Long?,
    var pixKey: String?,
    var pixKeyType : PixKeyType?,
    @field:NotNull
    var transactionType: TransactionType?,
    @field:NotNull
    var amount: BigDecimal?,
    var agencyNumber : Int?,
    var phoneNumber: String?
) {

}
