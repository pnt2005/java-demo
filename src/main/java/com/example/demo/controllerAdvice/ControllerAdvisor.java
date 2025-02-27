package com.example.demo.controllerAdvice;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.example.demo.customexception.FieldRequiredException;
import com.example.demo.model.ErrorResponseDTO;

@ControllerAdvice // catch exception
public class ControllerAdvisor extends ResponseEntityExceptionHandler {
	@ExceptionHandler(ArithmeticException.class) // discribe type of exception -> arithmetic
	public ResponseEntity<Object> handleArithmeticException(ArithmeticException ex, WebRequest request) {
		ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO();
		errorResponseDTO.setError(ex.getMessage());
		List<String> details = new ArrayList<>();
		details.add("khong chia duoc");
		errorResponseDTO.setDetail(details);
		return new ResponseEntity<>(errorResponseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(FieldRequiredException.class) // discribe type of exception -> arithmetic
	public ResponseEntity<Object> handleFieldRequiredException(FieldRequiredException ex, WebRequest request) {
		ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO();
		errorResponseDTO.setError(ex.getMessage());
		List<String> details = new ArrayList<>();
		details.add("khong co name");
		errorResponseDTO.setDetail(details);
		return new ResponseEntity<>(errorResponseDTO, HttpStatus.BAD_GATEWAY);
	}
}
