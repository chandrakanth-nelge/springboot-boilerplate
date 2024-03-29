package com.chan.nel.springboot.boilerplate.web.exception;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import com.chan.nel.springboot.boilerplate.constant.Status;
import com.chan.nel.springboot.boilerplate.dto.Response;
import com.chan.nel.springboot.boilerplate.exception.ErrorDetails;
import com.chan.nel.springboot.boilerplate.util.ErrorGenerator;

/**
 * @author Chandrakanth Nelge
 * @version 1.0
 * @since 1.0
 */
@RestController
@RequestMapping("${error.path:/error}")
public class NotFoundErrorController implements ErrorController {

	@Value("${error.path:/error}")
	private String errorPath;

	@Override
	public String getErrorPath() {
		return this.errorPath;
	}

	@GetMapping
	public ResponseEntity<Response<Void>> error(WebRequest request) {
		ErrorDetails errorDetails = ErrorGenerator.generateForCode("1003");
		Response<Void> errorResponse = new Response<>(Status.FAIL, HttpStatus.NOT_FOUND.value(),errorDetails);
		return new ResponseEntity<>(errorResponse, new HttpHeaders(), HttpStatus.NOT_FOUND);
	}

}