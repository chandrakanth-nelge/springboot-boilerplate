package com.chan.nel.springboot.boilerplate.web.controller;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import com.chan.nel.springboot.boilerplate.constant.Status;
import com.chan.nel.springboot.boilerplate.dto.Response;

/**
 * This ResponseBodyAdvice act as a wrapper for final response into
 * {@link Response} ONLY in case of SUCCESSFUL API CALL. No need to write code
 * for every endpoint at controller layer to create final {@code Response}
 * object. But we lose swagger documentation for final {@code Response}
 *
 * @author Chandrakanth Nelge
 * @version 1.0
 * @since 1.0
 */
@ControllerAdvice
public class CommonSuccessfulResponseAdvice implements ResponseBodyAdvice<Object> {

	/**
	 * {@inheritDoc} Only applicable for RestControllers written inside specific
	 * package.
	 */
	@Override
	public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
		return returnType.getDeclaringClass().getPackage().getName().equals("com.roche.navify.ow.web.controller");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
			Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request,
			ServerHttpResponse response) {
		if (body instanceof Response) {// defensive
			return body;
		}
		return new Response<>(Status.SUCCESS, ((ServletServerHttpResponse) response).getServletResponse().getStatus(),
				body);
	}

}