package com.exactaworks.exactabank.repository;

import com.exactaworks.exactabank.model.Transaction
import com.exactaworks.exactabank.model.TransactionType
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface TransactionRepository : JpaRepository<Transaction, Long> {

    @Query(
        """select t from Transaction t
        where (t.account.id = :id or t.targetAccount.id = :id)
        and (:type is null or t.transactionType = :type)
        order by t.dateTime DESC
        """
    )
    fun findByAccountIdAndTypeOrderByDateTimeDesc(
        @Param("id") id: Long,
        @Param("type") type: TransactionType?,
        pageable: Pageable
    ): Page<Transaction>
}