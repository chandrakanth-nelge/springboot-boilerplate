package com.chan.nel.springboot.boilerplate;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * @author Chandrakanth Nelge
 * @version 1.0
 * @since 1.0
 */
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
class SpringbootBoilerplateApplicationTests {

	@Autowired
	private ApplicationContext context;

	@Test
	public void verify_NoExceptions_whenSpringContextIsBootstrapped() {
		assertThat(this.context).isNotNull();
	}
}