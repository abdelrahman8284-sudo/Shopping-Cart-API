package com.abdelrahman.shoppingcart.controllers;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.abdelrahman.shoppingcart.dtos.requests.ImageDto;
import com.abdelrahman.shoppingcart.dtos.responses.ApiResponse;
import com.abdelrahman.shoppingcart.dtos.responses.ImageResponse;
import com.abdelrahman.shoppingcart.services.ImageService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
@Tag(name = "Image Management")
@RestController
@RequestMapping("/api/v1/images")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class ImageController {

	private final ImageService imageService;
	
	@PostMapping("/upload")
	@Operation(summary = "Upload Image (Admin)")
	public ResponseEntity<?> uploadImage(
			@RequestParam MultipartFile file ,
			@RequestParam Long productId) throws IOException,IllegalArgumentException{
		ImageDto imageDto = imageService.uploadImage(file, productId);
		
		return ResponseEntity.ok(
				Map.ofEntries(
						Map.entry("ImageId", imageDto.getId().toString()),
						Map.entry("OriginalName", imageDto.getFileName()),
						Map.entry("downloadUrl", imageDto.getDownloadUrl())));
	}
	
	@PostMapping("/upload-multi")
	@Operation(summary = "Upload Images (Admin)")
	public ResponseEntity<?> uploadImages(
			@RequestParam List<MultipartFile> files ,
			@RequestParam Long productId) throws IOException,IllegalArgumentException{
		List<ImageResponse> imagesDtos = imageService.uploadImages(files, productId);
		try {
			return ResponseEntity.ok().body(new ApiResponse("Upload success!",imagesDtos));	
		}catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Upload failed!", e.getMessage()));
		}
	}
	@GetMapping("/{imageId}")
	@Operation(summary = "Download Image (Admin)")
	public ResponseEntity<?> downloadImage(@PathVariable Long imageId) throws IOException,IllegalArgumentException{
		ImageDto imageDto = imageService.getImageMetaData(imageId);
		Resource resource = imageService.getImageResource(imageId);
		
		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
				"attachment; filename=\"" + imageDto.getFileName() + "\"")
		.contentType(MediaType.parseMediaType(imageDto.getFileType())).body(resource);
	}
	
	@PutMapping()
	public ResponseEntity<?> updateImage(
			@RequestParam MultipartFile file ,
			@RequestParam Long imageId) throws IOException,IllegalArgumentException{
		ImageDto imageDto = imageService.updateImage(file, imageId);
		
		return ResponseEntity.ok(
				Map.ofEntries(
						Map.entry("ImageId", imageDto.getId().toString()),
						Map.entry("OriginalName", imageDto.getFileName()),
						Map.entry("downloadUrl", imageDto.getDownloadUrl())));
	}
	
	@DeleteMapping("/{imageId}")
	@Operation(summary = "Delete Image (Admin)")
	public ResponseEntity<?> deleteImage(@PathVariable Long imageId) throws IOException{
		imageService.deleteImage(imageId);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@GetMapping("/product/{productId}")
	@Operation(summary = "Get Image by Product id (Admin)")
	public ResponseEntity<?> getByProductId(@PathVariable Long productId){
		return ResponseEntity.ok(imageService.getImageByProductId(productId));
	}
}
