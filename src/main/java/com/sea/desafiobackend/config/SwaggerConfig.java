package com.sea.desafiobackend.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        final String securitySchemeName = "bearerAuth";

        return new OpenAPI()
                .info(new Info()
                        .title("API Desafio SEA")
                        .version("1.0")
                        .description("Documentação interativa da API. Usa o endpoint de Login para obter o Token JWT e clica em 'Authorize' para testares as rotas protegidas."))
                // Adiciona o cadeado de segurança em todas as rotas
                .addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
                // Configura o esquema para aceitar o Token Bearer JWT
                .components(
                        new Components()
                                .addSecuritySchemes(securitySchemeName,
                                        new SecurityScheme()
                                                .name(securitySchemeName)
                                                .type(SecurityScheme.Type.HTTP)
                                                .scheme("bearer")
                                                .bearerFormat("JWT")
                                )
                );
    }

}
