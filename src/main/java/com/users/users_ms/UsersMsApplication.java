package com.users.users_ms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = "com.users.users_ms.infrastructure.adapters.client")
public class UsersMsApplication {

	public static void main(String[] args) {
		SpringApplication.run(UsersMsApplication.class, args);
	}

}
