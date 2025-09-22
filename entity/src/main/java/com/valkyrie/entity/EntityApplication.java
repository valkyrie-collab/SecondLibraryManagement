package com.valkyrie.entity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class EntityApplication {

	public static void main(String[] args) {
		SpringApplication.run(EntityApplication.class, args);
	}

}
