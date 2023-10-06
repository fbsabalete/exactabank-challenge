package com.exactaworks.exactabank.service

import com.exactaworks.exactabank.dto.OperationType
import com.exactaworks.exactabank.dto.TransactionDTO
import com.exactaworks.exactabank.dto.TransactionRequest
import com.exactaworks.exactabank.model.Transaction
import com.exactaworks.exactabank.model.TransactionType
import com.exactaworks.exactabank.repository.TransactionRepository
import com.exactaworks.exactabank.service.factory.NewTransactionStrategyFactory
import org.springframework.context.annotation.Lazy
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

@Service
class TransactionService(
    private val transactionRepository: TransactionRepository,
    @Lazy private val newTransactionStrategyFactory: NewTransactionStrategyFactory
) {

    @Transactional
    fun executeTransaction(request: TransactionRequest) {
        val transaction =
            newTransactionStrategyFactory.getStrategy(request.transactionType!!)
                .executeTransaction(request)
        saveTransaction(transaction)
    }

    fun saveTransaction(transaction: Transaction) {
        transactionRepository.save(transaction)
    }

    fun findTransactions(
        id: Long,
        type: TransactionType? = null,
        fromDate: LocalDate? = null,
        toDate: LocalDate? = null,
        pageable: Pageable
    ) : Page<TransactionDTO> {
        return transactionRepository.findByAccountIdAndTypeOrderByDateTimeDesc(
            id = id,
            type = type,
            fromDate = fromDate,
            toDate = toDate,
            pageable = pageable
        )
            .map { TransactionDTO(
                id = it.id!!,
                transactionType = it.transactionType,
                amount = it.amount,
                agencyNumber = it.agencyNumber,
                dateTime = it.dateTime!!,
                operation =
                if(it.targetAccount?.id == id || it.transactionType == TransactionType.DEPOSIT)
                    OperationType.CREDIT
                else OperationType.DEBIT
            ) }
    }

}