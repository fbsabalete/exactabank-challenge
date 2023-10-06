package com.exactaworks.exactabank.controller

import com.exactaworks.exactabank.dto.PageResponse
import com.exactaworks.exactabank.service.AccountService
import com.exactaworks.exactabank.service.AccountSummaryDTO
import org.springdoc.core.annotations.ParameterObject
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/accounts")
class AccountController(
    private val accountService: AccountService
) {

    @GetMapping("/summary/{accountId}")
    fun getAccountSummary(@PathVariable accountId: Long,
                          @PageableDefault(size = 10, page = 0) @ParameterObject pageable: Pageable) : PageResponse<AccountSummaryDTO> {
        return accountService.accountSummary(accountId, pageable)
    }

}