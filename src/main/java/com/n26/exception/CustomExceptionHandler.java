package com.n26.exception;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;

@RestControllerAdvice
public class CustomExceptionHandler {

	@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
	@ResponseBody
	@ExceptionHandler({ MethodArgumentNotValidException.class })
	public void methodArgumentNotValidException(MethodArgumentNotValidException ex) {
		System.out.println(ex);
	}

	// @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
	@ResponseBody
	@ExceptionHandler({ HttpMessageNotReadableException.class })
	public void httpMessageNotReadableException(HttpMessageNotReadableException ex, HttpServletResponse response) {
		if (ex.getCause() instanceof InvalidFormatException)
			response.setStatus(HttpStatus.UNPROCESSABLE_ENTITY.value());
		else
			response.setStatus(HttpStatus.BAD_REQUEST.value());

	}

}
