package com.example.policycenter.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.Components;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    private static final String SECURITY_SCHEME_NAME = "bearerAuth";

    @Bean
    public OpenAPI motorPolicyCenterAPI() {

        return new OpenAPI()
                .info(new Info()
                        .title("Motor Insurance PolicyCenter API")
                        .description("Complete teaching API for Quotes and Policies")
                        .version("1.0.0")
                )

                // Add JWT security requirement globally
                .addSecurityItem(new SecurityRequirement().addList(SECURITY_SCHEME_NAME))

                // Register JWT Bearer token security scheme
                .components(new Components()
                        .addSecuritySchemes(SECURITY_SCHEME_NAME,   // ← note the plural
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")
                                        .in(SecurityScheme.In.HEADER)
                                        .name("Authorization")
                        )
                );
    }
}
