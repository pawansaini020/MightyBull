package com.pawan.MightyBull;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@SpringBootApplication
@EnableMongoAuditing
public class MightyBullApplication {

	public static void main(String[] args) {
		SpringApplication.run(MightyBullApplication.class, args);
	}

}
