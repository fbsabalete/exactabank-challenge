package com.exactaworks.exactabank.configuration

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Contact
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.info.License
import io.swagger.v3.oas.models.servers.Server
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
class OpenApiConfig {

    @Bean
    fun openApiInformation(): OpenAPI? {
        val localServer: Server = Server()
            .url("http://localhost:8080")
            .description("Localhost Server URL")
        val contact: Contact = Contact()
            .email("fb.sabalete@gmail.com")
            .name("Fernando Sabalete")
        val info: Info = Info()
            .contact(contact)
            .description("Projeto de desafio para a empresa Exactaworks")
            .title("Exactabank")
            .version("1.0.0")
            .license(License().name("Apache 2.0").url("http://springdoc.org"))
        return OpenAPI().info(info).addServersItem(localServer)
    }

}