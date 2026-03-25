package com.abdelrahman.shoppingcart.services;

import java.io.IOException;
import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import com.abdelrahman.shoppingcart.dtos.requests.ImageDto;
import com.abdelrahman.shoppingcart.dtos.responses.ImageResponse;

public interface ImageService {

	ImageDto uploadImage(MultipartFile file ,Long productId)throws IOException;
	
	List<ImageResponse> uploadImages(List<MultipartFile> files ,Long productId) throws IOException;
	
	ImageDto getImageMetaData(Long imageId)throws IOException;
	
	Resource getImageResource(Long imageId)throws IOException;
	
	void deleteImage(Long id) throws IOException ;
	
	ImageDto updateImage(MultipartFile file , Long imageId) throws IOException;
	
	List<ImageDto> getImageByProductId(Long productId);
}
