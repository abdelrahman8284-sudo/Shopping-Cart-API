package com.abdelrahman.shoppingcart.dtos.responses;

import lombok.Data;

@Data
public class ImageResponse {

	private Long id ;
	private String fileName;
	private String downloadUrl;
}
