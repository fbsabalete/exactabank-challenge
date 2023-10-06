package com.exactaworks.exactabank.controller

import com.exactaworks.exactabank.dto.PageMetaData
import com.exactaworks.exactabank.dto.PageResponse
import com.exactaworks.exactabank.dto.TransactionDTO
import com.exactaworks.exactabank.dto.TransactionRequest
import com.exactaworks.exactabank.model.TransactionType
import com.exactaworks.exactabank.service.AccountSummaryDTO
import com.exactaworks.exactabank.service.TransactionService
import com.exactaworks.exactabank.service.factory.NewTransactionStrategyFactory
import jakarta.validation.Valid
import org.jetbrains.annotations.NotNull
import org.springdoc.core.annotations.ParameterObject
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDate


@RestController
@RequestMapping("/api/transactions")
class TransactionController(
    private val transactionService: TransactionService
) {

    @PostMapping
    fun createTransaction(@RequestBody @Valid @NotNull request : TransactionRequest) {
        transactionService.executeTransaction(request);
    }

    @GetMapping("/account/{accountId}")
    fun getTransations(
        @PathVariable accountId: Long,
        @RequestParam(required = false) transactionType: TransactionType? = null,
        @RequestParam(required = false) fromDate: LocalDate?,
        @RequestParam(required = false) toDate: LocalDate? = LocalDate.now().plusDays(15),//TODO filtro por data
        @PageableDefault(size = 10, page = 0) @ParameterObject pageable: PageRequest
    ): PageResponse<MutableList<TransactionDTO>> {
        val pagedTransactions = transactionService.findTransactions(accountId, transactionType, pageable)
        return PageResponse(
            data = pagedTransactions.content,
            meta = PageMetaData(
                currentPage = pagedTransactions.number,
                hasNext = pagedTransactions.hasNext(),
                totalRecords = pagedTransactions.totalElements,
                totalPages = pagedTransactions.totalPages
            )
        )

    }

}

