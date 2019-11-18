package com.chan.nel.springboot.boilerplate.util;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import lombok.Getter;

/**
 * @author Chandrakanth Nelge
 * @version 1.0
 * @since 1.0
 */
@Component
public class ErrorGeneratorInitializer {

	@Getter
	@Autowired
	private MessageSource errorMessageSource;

	@PostConstruct
	public void setErrorMessageSource() {
		ErrorGenerator.setErrorMessageSource(errorMessageSource);
	}
}