package com.every.expence;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@SpringBootApplication
@EnableMongoAuditing
public class ExpenceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExpenceApplication.class, args);
	}

}
