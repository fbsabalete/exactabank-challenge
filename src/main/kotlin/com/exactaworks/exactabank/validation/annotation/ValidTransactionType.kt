package com.exactaworks.exactabank.validation.annotation

import com.exactaworks.exactabank.model.TransactionType
import com.exactaworks.exactabank.validation.TransactionTypeValidator
import jakarta.validation.Constraint
import jakarta.validation.Payload
import kotlin.reflect.KClass

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [TransactionTypeValidator::class])
@MustBeDocumented
annotation class ValidTransactionType (
    val message: String = "Required field {field} not present",
    val type : TransactionType,
    val typeField : String = "transactionType",
    val requiredFields : Array<String> = [],

    val groups: Array<KClass<Any>> = [],
    val payload: Array<KClass<Payload>> = []
) {
    @Target(AnnotationTarget.CLASS)
    @Retention(AnnotationRetention.RUNTIME)
    annotation class List (
        val value : Array<ValidTransactionType> = []
    )
}
