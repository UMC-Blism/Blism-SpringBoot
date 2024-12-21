package com.example.blism;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class BlismApplication {

	public static void main(String[] args) {
		SpringApplication.run(BlismApplication.class, args);
	}

}
