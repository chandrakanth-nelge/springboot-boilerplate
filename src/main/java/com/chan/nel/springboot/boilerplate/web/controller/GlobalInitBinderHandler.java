package com.chan.nel.springboot.boilerplate.web.controller;

import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;

/**
 * @author Chandrakanth Nelge
 * @version 1.0
 * @since 1.0
 */
@ControllerAdvice
public class GlobalInitBinderHandler {
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		StringTrimmerEditor stringTrimmer = new StringTrimmerEditor(true);
		binder.registerCustomEditor(String.class, stringTrimmer);
	}
}