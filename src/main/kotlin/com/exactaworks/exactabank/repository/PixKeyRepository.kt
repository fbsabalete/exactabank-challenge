package com.exactaworks.exactabank.repository;

import com.exactaworks.exactabank.model.PixKey
import org.springframework.data.jpa.repository.JpaRepository

interface PixKeyRepository : JpaRepository<PixKey, Long> {
}