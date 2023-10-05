package com.exactaworks.exactabank.validation

import com.exactaworks.exactabank.dto.TransactionRequest
import com.exactaworks.exactabank.dto.ValidTransactionType
import com.exactaworks.exactabank.model.TransactionType
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import org.hibernate.validator.internal.engine.constraintvalidation.ConstraintValidatorContextImpl
import org.springframework.beans.BeanWrapperImpl

class TransactionTypeValidator : ConstraintValidator<ValidTransactionType, TransactionRequest> {

    var typeField : String? = null
    var requiredFields : Array<String> = arrayOf()
    var type: TransactionType? = null

    override fun initialize(constraintAnnotation: ValidTransactionType?) {
        this.typeField = constraintAnnotation!!.typeField
        this.requiredFields = constraintAnnotation.requiredFields
        this.type = constraintAnnotation.type
    }

    override fun isValid(request: TransactionRequest?, context: ConstraintValidatorContext?): Boolean {
        if (request?.transactionType != this.type) {
            return true;
        }
        val beanWrapperImpl = BeanWrapperImpl(request!!)
        val missingFields = this.requiredFields.filter {
            beanWrapperImpl.getPropertyValue(it) == null
        }
        if(missingFields.isNotEmpty()) {
            (context as ConstraintValidatorContextImpl).addMessageParameter("field", missingFields.toString())
            return false
        }
        return true
    }

}
