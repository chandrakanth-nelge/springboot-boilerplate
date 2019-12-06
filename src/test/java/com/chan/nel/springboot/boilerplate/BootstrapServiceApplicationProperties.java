package com.chan.nel.springboot.boilerplate;

import java.util.Set;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

/**
 * @author Chandrakanth Nelge
 * @version 1.0
 * @since 1.0
 */
@Configuration
@ConfigurationProperties
@Data
public class BootstrapServiceApplicationProperties {

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