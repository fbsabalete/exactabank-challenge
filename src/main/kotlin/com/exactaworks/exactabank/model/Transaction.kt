package com.exactaworks.exactabank.model

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import java.math.BigDecimal
import java.time.LocalDateTime

@Entity
class Transaction(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) var id: Long? = null,
    @ManyToOne @JoinColumn(name = "ACCOUNT_FK") var account: Account,
    @ManyToOne @JoinColumn(name = "TARGET_ACCOUNT_FK") var targetAccount: Account? = null,
    @Enumerated(EnumType.STRING) @Column(name = "TRANSACTION_TYPE") var transactionType: TransactionType,
    @Column(name = "AMOUNT", columnDefinition = "DECIMAL(10,2)") var amount : BigDecimal,
    @Column(name = "AGENCY_NUMBER") var agencyNumber: Int? = null,
    @Column(name = "TRANSACTION_DATE_TIME") @CreationTimestamp var dateTime: LocalDateTime? = null
)

enum class TransactionType {
    PIX, PHONE_RECHARGE, DEPOSIT, WITHDRAWAL
}