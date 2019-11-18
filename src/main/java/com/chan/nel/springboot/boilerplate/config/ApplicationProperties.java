package com.chan.nel.springboot.boilerplate.config;

import java.util.Set;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

/**
 * @author Chandrakanth Nelge
 * @version 1.0
 * @since 1.0
 */
@Configuration
@EnableConfigurationProperties
@ConfigurationProperties
@Data
public class ApplicationProperties {

	private Sort sort = new Sort();

	@Data
	public static class Sort {
		private User user = new User();

		@Data
		public static class User {
			private String defaultParam;

			private Set<String> params;
		}
	}
}