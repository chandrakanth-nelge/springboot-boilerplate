package com.chan.nel.springboot.boilerplate.util;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

import com.chan.nel.springboot.boilerplate.exception.ErrorDetails;

/**
 * @author Chandrakanth Nelge
 * @version 1.0
 * @since 1.0
 */
public final class ErrorGenerator {

	private static final String DEFAULT_MESSAGE = "Unkwon error. Please contact support";

	@SuppressWarnings("squid:S3008")
	private static MessageSource ERROR_MESSAGE_SOURCE;

	private ErrorGenerator() {
		throw new AssertionError();
	}

	public static void setErrorMessageSource(MessageSource errorMessageSource) {
		ErrorGenerator.ERROR_MESSAGE_SOURCE = errorMessageSource;
	}

	public static ErrorDetails generateForCode(String code) {
		return new ErrorDetails(code,
				ERROR_MESSAGE_SOURCE.getMessage(code, null, DEFAULT_MESSAGE, LocaleContextHolder.getLocale()));
	}

	public static ErrorDetails generateForCodeWithTarget(String code, String target) {
		return new ErrorDetails(code,
				ERROR_MESSAGE_SOURCE.getMessage(code, null, DEFAULT_MESSAGE, LocaleContextHolder.getLocale()), target);
	}

	public static ErrorDetails generateForCodeWithArguments(String code, Object... args) {
		return new ErrorDetails(code,
				ERROR_MESSAGE_SOURCE.getMessage(code, args, DEFAULT_MESSAGE, LocaleContextHolder.getLocale()));
	}

	public static ErrorDetails generateForCodeWithArgumentsAndTarget(String code, String target, Object... args) {
		return new ErrorDetails(code,
				ERROR_MESSAGE_SOURCE.getMessage(code, args, DEFAULT_MESSAGE, LocaleContextHolder.getLocale()), target);
	}
}