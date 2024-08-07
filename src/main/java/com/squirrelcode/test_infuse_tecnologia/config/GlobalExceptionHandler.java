package com.squirrelcode.test_infuse_tecnologia.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.squirrelcode.test_infuse_tecnologia.service.OrderService.DuplicateOrderException;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(DuplicateOrderException.class)
	public ResponseEntity<Map<String, Object>> handleDuplicateOrderException(DuplicateOrderException ex) {
		Map<String, Object> responseBody = new HashMap<>();
		responseBody.put("success", false);
		responseBody.put("message", ex.getMessage());

		return ResponseEntity.status(HttpStatus.CONFLICT).body(responseBody);
	}

	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<Map<String, Object>> handleDataIntegrityViolationException(
			DataIntegrityViolationException ex) {
		Map<String, Object> responseBody = new HashMap<>();
		responseBody.put("success", false);
		responseBody.put("message", "Database error: " + ex.getMessage());

		return ResponseEntity.status(HttpStatus.CONFLICT).body(responseBody);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<Map<String, Object>> handleGenericException(Exception ex) {
		Map<String, Object> responseBody = new HashMap<>();
		responseBody.put("success", false);
		responseBody.put("message", "An unexpected error occurred: " + ex.getMessage());

		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBody);
	}
}
