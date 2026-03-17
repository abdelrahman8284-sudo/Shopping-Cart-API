package com.abdelrahman.shoppingcart.exceptions;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter@Builder@AllArgsConstructor
public class ErrorResponseDto {

	private final boolean success =false;
	
	private int status;
	
	private String message;
	
	private List<String> details;
	
	@Builder.Default
	private LocalDateTime timestamp=LocalDateTime.now();
	
	private String path;
	
	public ErrorResponseDto() {
		this.timestamp=LocalDateTime.now();
	}
	
	public ErrorResponseDto(String message,List<String> details,String path,int status) {
		this.details=details;
		this.message=message;
		this.path=path;
		this.status=status;
		this.timestamp=LocalDateTime.now();
	}
	
	public ErrorResponseDto(String message,List<String> details,int status) {
		this.details=details;
		this.message=message;
		this.status=status;
		this.timestamp=LocalDateTime.now();
	}
}
