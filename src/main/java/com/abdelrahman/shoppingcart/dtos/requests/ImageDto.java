package com.abdelrahman.shoppingcart.dtos.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Builder@Setter@Getter@AllArgsConstructor@NoArgsConstructor
public class ImageDto {
	
	private Long id;
	
	private String fileName;
	
	private String fileType;
	
	private String filePath;
	
	//private byte[] image;
	
	private String downloadUrl;

	private Long productId;
}
