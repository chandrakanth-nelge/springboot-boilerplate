package com.chan.nel.springboot.boilerplate;

import org.springframework.beans.factory.annotation.Value;
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

	@Value("${spring.profiles.active}")
	private static String activeProfile;

	public static void main(String[] args) {
		System.out.println("Chandra");
		SpringApplication.run(SpringbootBoilerplateApplication.class, args);
		System.out.println("activeProfile :: " + activeProfile);
	}
}