package com.chan.nel.springboot.boilerplate.config;

import javax.validation.ConstraintValidatorFactory;

import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.SpringConstraintValidatorFactory;

/**
 * @author Chandrakanth Nelge
 * @version 1.0
 * @since 1.0
 */
@Configuration
public class ValidatorConfiguration {

	@Bean
	public ConstraintValidatorFactory constraintValidatorFactory(AutowireCapableBeanFactory beanFactory) {
		return new SpringConstraintValidatorFactory(beanFactory);
	}
}