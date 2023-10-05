package com.exactaworks.exactabank.model

import jakarta.persistence.*

@Entity
@Table(uniqueConstraints=[
    UniqueConstraint(columnNames = ["PIX_KEY", "KEY_TYPE"])
])
class PixKey (
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) var id : Long,
    @Enumerated(EnumType.STRING) @Column(name = "KEY_TYPE") var keyType : PixKeyType,
    @Column(name = "PIX_KEY") var key : String,
    @ManyToOne @JoinColumn(name = "ACCOUNT_FK") var account : Account
)

enum class PixKeyType {
    PHONE_NUMBER, CPF, EMAIL
}
