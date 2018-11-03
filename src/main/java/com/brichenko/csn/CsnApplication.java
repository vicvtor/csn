package com.brichenko.csn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CsnApplication {

	public static void main(String[] args) {
		SpringApplication.run(CsnApplication.class, args);
	}
}
