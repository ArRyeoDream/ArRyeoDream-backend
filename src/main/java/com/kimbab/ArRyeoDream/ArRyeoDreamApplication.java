package com.kimbab.ArRyeoDream;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class ArRyeoDreamApplication {
	public static void main(String[] args) {
		SpringApplication.run(ArRyeoDreamApplication.class, args);
	}

}
