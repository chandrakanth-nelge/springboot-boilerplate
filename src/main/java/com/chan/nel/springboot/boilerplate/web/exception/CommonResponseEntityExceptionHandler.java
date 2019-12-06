package com.chan.nel.springboot.boilerplate.web.exception;

import java.util.Set;

import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.chan.nel.springboot.boilerplate.constant.Status;
import com.chan.nel.springboot.boilerplate.dto.Response;
import com.chan.nel.springboot.boilerplate.exception.ErrorDetails;
import com.chan.nel.springboot.boilerplate.util.ErrorGenerator;

import lombok.extern.log4j.Log4j2;

/**
 * Common exception handler to handle generic rest exceptions
 *
 * @author Chandrakanth Nelge
 * @version 1.0
 * @since 1.0
 */
@Log4j2
@RestControllerAdvice
public class CommonResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

	@Override
	public ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex,
																	  HttpHeaders headers, HttpStatus status, 
																	  WebRequest request) {
		log.error("<<HttpRequestMethodNotSupportedException>>", ex);
		Set<HttpMethod> supportedMethods = ex.getSupportedHttpMethods();
		if (!CollectionUtils.isEmpty(supportedMethods)) {
			headers.setAllow(supportedMethods);
		}
		
		ErrorDetails errorDetails = ErrorGenerator.generateForCode("1001");
		Response<Void> errorResponse = new Response<>(Status.CLIENT_ERROR, status.value(),errorDetails);
		return new ResponseEntity<>(errorResponse, headers, status);
	}

	@Override
	public ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers,
			                                                    HttpStatus status, WebRequest request) {
		log.error("<<NoHandlerFoundException>>", ex);
		ErrorDetails errorDetails = ErrorGenerator.generateForCode("1003");
		Response<Void> errorResponse = new Response<>(Status.CLIENT_ERROR, status.value(),errorDetails);
		return new ResponseEntity<>(errorResponse, headers, status);
	}

	@Override
	public ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex,
																	   HttpHeaders headers, HttpStatus status, 
																	   WebRequest request) {
		log.error("<<MissingServletRequestParameterException>>", ex);
		ErrorDetails errorDetails = ErrorGenerator.generateForCode("1002");
		Response<Void> errorResponse = new Response<>(Status.CLIENT_ERROR, status.value(),errorDetails);
		return new ResponseEntity<>(errorResponse, headers, status);
	}

	@Override
	public ResponseEntity<Object> handleServletRequestBindingException(ServletRequestBindingException ex,
																	   HttpHeaders headers, HttpStatus status, 
																	   WebRequest request) {
		log.error("<<ServletRequestBindingException>>", ex);
		ErrorDetails errorDetails = ErrorGenerator.generateForCode("1002");
		Response<Void> errorResponse = new Response<>(Status.CLIENT_ERROR, status.value(),errorDetails);
		return new ResponseEntity<>(errorResponse, headers, status);
	}

	@Override
	public ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers, 
													 HttpStatus status, WebRequest request) {
		log.error("<<TypeMismatchException>>", ex);
		ErrorDetails errorDetails = ErrorGenerator.generateForCode("1002");
		Response<Void> errorResponse = new Response<>(Status.CLIENT_ERROR, status.value(),errorDetails);
		return new ResponseEntity<>(errorResponse, headers, status);
	}

	@Override
	public ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, 
															   HttpHeaders headers,HttpStatus status, 
															   WebRequest request) {
		log.error("<<HttpMessageNotReadableException>>", ex);
		ErrorDetails errorDetails = ErrorGenerator.generateForCode("1002");
		Response<Void> errorResponse = new Response<>(Status.CLIENT_ERROR, status.value(),errorDetails);
		return new ResponseEntity<>(errorResponse, headers, status);
	}

	@Override
	public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers,
															   HttpStatus status, WebRequest request) {
		log.error("<<MethodArgumentNotValidException>>", ex);
		Response<Void> errorResponse;
		if (ex.getBindingResult().hasFieldErrors()) {
			ErrorDetails[] arr = ex.getBindingResult().getFieldErrors().stream()
					.map(error -> ErrorGenerator.generateForCode(error.getDefaultMessage()))
					.toArray(ErrorDetails[]::new);
			errorResponse = new Response<>(Status.CLIENT_ERROR, status.value(), arr);
		} else {
			ErrorDetails errorDetails = ErrorGenerator.generateForCode("1002");
			errorResponse = new Response<>(Status.CLIENT_ERROR, status.value(),errorDetails);
		}
		return new ResponseEntity<>(errorResponse, headers, status);
	}

	@Override
	public ResponseEntity<Object> handleMissingServletRequestPart(MissingServletRequestPartException ex,
																  HttpHeaders headers, HttpStatus status, 
																  WebRequest request) {
		log.error("<<MissingServletRequestPartException>>", ex);
		ErrorDetails errorDetails = ErrorGenerator.generateForCode("1002");
		Response<Void> errorResponse = new Response<>(Status.CLIENT_ERROR, status.value(),errorDetails);
		return new ResponseEntity<>(errorResponse, headers, status);
	}

	@Override
	public ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers, HttpStatus status,
													  WebRequest request) {
		log.error("<<BindException>>", ex);
		ErrorDetails errorDetails = ErrorGenerator.generateForCode("1002");
		Response<Void> errorResponse = new Response<>(Status.CLIENT_ERROR, status.value(),errorDetails);
		return new ResponseEntity<>(errorResponse, headers, status);
	}
}