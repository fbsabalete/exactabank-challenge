package com.exactaworks.exactabank.client

import com.exactaworks.exactabank.configuration.FeignConfig
import com.exactaworks.exactabank.dto.PhoneCreditApiRequest
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody

@FeignClient(url = "\${rest.apis.phone-credit.url}", name = "phoneCreditApi", configuration = [FeignConfig::class])
@Component
interface PhoneCreditApiClient {

    @PostMapping
    fun rechargePhone(@RequestBody request: PhoneCreditApiRequest) : ResponseEntity<Void>

}