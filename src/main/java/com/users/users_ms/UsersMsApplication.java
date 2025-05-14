package com.users.users_ms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = "com.users.users_ms.infrastructure.adapters.client")
public class UsersMsApplication {

	public static void main(String[] args) {
		System.out.println("DB_URL: " + System.getenv("DB_URL"));
		System.out.println("DB_USERNAME: " + System.getenv("DB_USERNAME"));
		String password = System.getenv("DB_PASSWORD");
		System.out.println("🔐 Password: " + password);
		System.out.println("📏 Length: " + password.length());

		for(int i = 0; i < password.length(); i++) {
			System.out.println("Carácter " + i + ": " + password.charAt(i));
		}

		SpringApplication.run(UsersMsApplication.class, args);


	}

}
