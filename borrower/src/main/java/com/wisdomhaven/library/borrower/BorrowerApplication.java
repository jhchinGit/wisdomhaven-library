package com.wisdomhaven.library.borrower;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class BorrowerApplication {

	public static void main(String[] args) {
		SpringApplication.run(BorrowerApplication.class, args);
	}

}
