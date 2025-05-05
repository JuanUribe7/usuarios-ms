package com.users.users_ms.commons.configurations;

import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI apiInfo() {
        return new OpenAPI()
                .info(new Info()
                        .title("Users Microservice API")
                        .version("1.0")
                        .description("API documentation for user management.")
                        .contact(new Contact()
                                .name("Juan Uribe")
                                .email("uribej030@gmail.com")));
    }
}
