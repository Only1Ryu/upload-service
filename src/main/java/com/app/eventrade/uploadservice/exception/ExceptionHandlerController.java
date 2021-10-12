package com.app.eventrade.uploadservice.exception;

import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.app.eventrade.uploadservice.dto.Response;


@RestControllerAdvice
public class ExceptionHandlerController {

	private static final Logger logger = LoggerFactory.getLogger(ExceptionHandlerController.class);
	private static LocalDateTime timeStamp = LocalDateTime.now();
	
	@ExceptionHandler(value = {Exception.class,RuntimeException.class})
	@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
	public ResponseEntity<Response> defaultErrorHandler(Exception exception)
	{
		logger.error(exception.toString());
		Response response = new Response();
		response.setResponse(exception.getLocalizedMessage());
		response.setTimeStamp(timeStamp);
		response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		return new ResponseEntity<>(response,HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(value = {NotFoundException.class})
	@ResponseStatus(code = HttpStatus.NOT_FOUND)
	public ResponseEntity<Response> NotFoundHandler(NotFoundException exception)
	{
		logger.error(exception.toString());
		Response response = new Response();
		response.setResponse(exception.getLocalizedMessage());
		response.setTimeStamp(timeStamp);
		response.setStatusCode(HttpStatus.NOT_FOUND.value());
		return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(value = {InvalidDataException.class})
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	public ResponseEntity<Response> InvalidHandler(InvalidDataException exception)
	{
		logger.error(exception.toString());
		Response response = new Response();
		response.setResponse(exception.getLocalizedMessage());
		response.setTimeStamp(timeStamp);
		response.setStatusCode(HttpStatus.BAD_REQUEST.value());		
		return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(value = {CustomException.class})
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	public ResponseEntity<Response> CustomHandler(CustomException exception)
	{
		logger.error(exception.toString());
		Response response = new Response();
		response.setResponse(exception.getLocalizedMessage());
		response.setTimeStamp(timeStamp);
		response.setStatusCode(HttpStatus.CONFLICT.value());
		return new ResponseEntity<>(response,HttpStatus.CONFLICT);
	}
}
