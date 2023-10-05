package com.exactaworks.exactabank.repository;

import com.exactaworks.exactabank.model.Transaction
import org.springframework.data.jpa.repository.JpaRepository

interface TransactionRepository : JpaRepository<Transaction, Long> {
}