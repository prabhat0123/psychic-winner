package com.n26.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomExceptionHandler {

	@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
	@ResponseBody
	@ExceptionHandler({ MethodArgumentNotValidException.class })
	public void methodArgumentNotValidException(MethodArgumentNotValidException ex) {

	}

	@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
	@ResponseBody
	@ExceptionHandler({ HttpMessageNotReadableException.class })
	public void httpMessageNotReadableException(HttpMessageNotReadableException ex) {

	}

}
