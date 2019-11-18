package com.chan.nel.springboot.boilerplate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author Chandrakanth Nelge
 * @version 1.0
 * @since 1.0
 */
@SpringBootApplication
@EnableTransactionManagement
public class SpringbootBoilerplateApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootBoilerplateApplication.class, args);
	}
}