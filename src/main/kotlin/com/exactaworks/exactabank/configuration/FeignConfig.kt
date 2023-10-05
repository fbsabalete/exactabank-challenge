package com.exactaworks.exactabank.configuration

import com.exactaworks.exactabank.client.PhoneCreditApiClient
import feign.Client
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@EnableFeignClients(clients = [PhoneCreditApiClient::class])
class FeignConfig {

    @Bean
    fun feignClient(): Client {
        return Client.Default(null, null)
    }

}