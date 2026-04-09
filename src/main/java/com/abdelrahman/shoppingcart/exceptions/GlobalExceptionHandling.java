package com.abdelrahman.shoppingcart.exceptions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;

@ControllerAdvice
public class GlobalExceptionHandling extends ResponseEntityExceptionHandler {

	@ExceptionHandler(RecordNotFoundException.class)
	public ResponseEntity<?> handlingRecordNotFound(RecordNotFoundException ex,HttpServletRequest http){
		String path = (String) http.getRequestURI();
		ErrorResponseDto errorResponse = new ErrorResponseDto(
				ex.getLocalizedMessage(),
				Arrays.asList(ex.getMessage()),
				path,
				HttpStatus.NOT_FOUND.value()
				);
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
	}
	
	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<?> handlingRuntimeException(RuntimeException ex,HttpServletRequest http){
		String path = (String) http.getRequestURI();
		ErrorResponseDto errorResponse = new ErrorResponseDto(
				ex.getLocalizedMessage(),
				Arrays.asList(ex.getMessage()),
				path,
				HttpStatus.BAD_REQUEST.value()
				);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
	}
	

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
	        HttpHeaders headers, HttpStatusCode status, WebRequest request) {
	    
	    List<String> errors = new ArrayList<>();
	    ex.getBindingResult().getFieldErrors().forEach(f -> errors.add(f.getDefaultMessage()));
	    ex.getBindingResult().getGlobalErrors().forEach(o -> errors.add(o.getDefaultMessage()));
	    
	    String path = ((ServletWebRequest)request).getRequest().getRequestURI();

	    ErrorResponseDto errorResponse = new ErrorResponseDto(
	            "Validation Error",
	            errors,
	            path,
	            status.value() 
	            );
	    
	    return new ResponseEntity<>(errorResponse, headers, status);
	}
	
	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<Object> handleConstraintViolation(ConstraintViolationException ex, WebRequest request) {
	    List<String> errors = new ArrayList<>();
	    ex.getConstraintViolations().forEach(violation -> {
	        errors.add(violation.getPropertyPath() + ": " + violation.getMessage());
	    });

	    String path = ((ServletWebRequest)request).getRequest().getRequestURI();
	    ErrorResponseDto errorResponse = new ErrorResponseDto(
	            "Parameter Validation Error",
	            errors,
	            path,
	            HttpStatus.BAD_REQUEST.value()
	    );

	    return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
	}

	
	@ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGlobalException(Exception ex, HttpServletRequest http) {
        
        String path = http.getRequestURI();
        
        ErrorResponseDto errorResponse = new ErrorResponseDto(
				ex.getLocalizedMessage(),
				Arrays.asList(ex.getMessage()),
				path,
				HttpStatus.INTERNAL_SERVER_ERROR.value()
				);
        
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
	
	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<?> handleDataIntegrity(DataIntegrityViolationException ex, HttpServletRequest http) {
	    ErrorResponseDto errorResponse = new ErrorResponseDto(
	            "Database Conflict",
	            Arrays.asList("Cannot delete this item because it has related records. Delete the related items first."),
	            http.getRequestURI(),
	            HttpStatus.CONFLICT.value()
	    );
	    return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
	}
	
	@ExceptionHandler(BadCredentialsException.class)
	public ResponseEntity<?> handleBadCredentialsException(BadCredentialsException ex ){
		ErrorResponseDto errorResponse = ErrorResponseDto.builder()
				.message("Invalid Username or password")
				.status(HttpStatus.UNAUTHORIZED.value())
				.details(Arrays.asList(ex.getMessage()))
				.build();
		return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
	}
	
	@ExceptionHandler(AuthorizationDeniedException.class)
	public ResponseEntity<?> handleAuthorizationDeniedException(AuthorizationDeniedException ex){
		ErrorResponseDto error = ErrorResponseDto.builder()
				.message("This user is not allowed")
				.status(HttpStatus.UNAUTHORIZED.value())
				.build();
		return new ResponseEntity<>(error,HttpStatus.UNAUTHORIZED);
	}
	
	@ExceptionHandler(IllegalOrderStateException.class)
	public ResponseEntity<?> handleIllegalOrderStateException(IllegalOrderStateException ex){
		ErrorResponseDto error = ErrorResponseDto.builder()
				.message(ex.getLocalizedMessage())
				.details(Arrays.asList(ex.getMessage()))
				.status(HttpStatus.NOT_ACCEPTABLE.value())
				.build();
		return new ResponseEntity<>(error,HttpStatus.NOT_ACCEPTABLE);
	}
	
	@ExceptionHandler(InsufficientStockException.class)
	public ResponseEntity<?> handleInsufficientStockException(InsufficientStockException ex){
		ErrorResponseDto error = ErrorResponseDto.builder()
				.message(ex.getLocalizedMessage())
				.details(Arrays.asList(ex.getMessage()))
				.status(HttpStatus.CONFLICT.value())
				.build();
		return new ResponseEntity<>(error,HttpStatus.NOT_ACCEPTABLE);
	}
}
