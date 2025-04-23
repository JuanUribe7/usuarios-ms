package com.users.users_ms.commons.configurations.swagger;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI apiInfo() {
        return new OpenAPI()
                .info(new Info()
                        .title("Usuarios Microservicio API")
                        .version("1.0")
                        .description("Documentación de la API para la gestión de usuarios.")
                        .contact(new Contact()
                                .name("Tu Nombre")
                                .email("tuemail@ejemplo.com")));
    }
}