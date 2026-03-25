package com.abdelrahman.shoppingcart.services.impl;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.abdelrahman.shoppingcart.configurations.ImageProperties;
import com.abdelrahman.shoppingcart.dtos.requests.ImageDto;
import com.abdelrahman.shoppingcart.dtos.responses.ImageResponse;
import com.abdelrahman.shoppingcart.exceptions.RecordNotFoundException;
import com.abdelrahman.shoppingcart.mappers.ImageMapper;
import com.abdelrahman.shoppingcart.models.Image;
import com.abdelrahman.shoppingcart.models.Product;
import com.abdelrahman.shoppingcart.repositories.ImageRepo;
import com.abdelrahman.shoppingcart.repositories.ProductRepo;
import com.abdelrahman.shoppingcart.services.ImageService;
import com.abdelrahman.shoppingcart.services.ImageStorageService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@RequiredArgsConstructor
@Log4j2
public class ImageServiceImpl implements ImageService {

	private final ImageProperties properties;
	private final ImageStorageService storageService;
	private final ImageRepo imageRepo;
	private final ProductRepo productRepo;
	private final ImageMapper mapper;
	
	@Override
	@Transactional
	public ImageDto uploadImage(MultipartFile file, Long productId) throws IOException {
		validateImage(file);
		
		Product product = productRepo.findById(productId).orElseThrow(()->new RecordNotFoundException("Product not found"));

		String storagePath;
		try(InputStream input = file.getInputStream()){
			storagePath = storageService.storeFile(input, file.getOriginalFilename());
		}
		//ImageDto dto = ImageDto.builder()
//				.fileName(file.getOriginalFilename())
//				.filePath(storagePath)
//				.fileType(file.getContentType())
//				.image(file.getBytes())
//				.productId(productId)
//				.build();
		Image image = Image.builder()
				.fileName(file.getOriginalFilename())
				.filePath(storagePath)
				.fileType(file.getContentType())
				.image(file.getBytes())
				.product(product)
				.build();
		image.setDownloadUrl("/api/v1/images/"+image.getId());
		Image savedImage = imageRepo.save(image);

		log.info("Product :" ,savedImage.getProduct());
		ImageDto dto = mapper.toDto(savedImage);
		return dto;
	}
	
	
	@Override
	@Transactional
	public List<ImageResponse> uploadImages(List<MultipartFile> files, Long productId) throws IOException {
		Product product = productRepo.findById(productId).orElseThrow(()->new RecordNotFoundException("Product not found"));

		List<Image> imagesToSave = new ArrayList<>();
		for (MultipartFile file : files) {
			validateImage(file);
			String storagePath ;
			try(InputStream input = file.getInputStream()){
				storagePath = storageService.storeFile(input, file.getOriginalFilename());
			}
			Image image = Image.builder()
					.fileName(file.getOriginalFilename())
					.filePath(storagePath)
					.fileType(file.getContentType())
					.image(file.getBytes())
					.product(product)
					.build();
			imagesToSave.add(image);
				
		}
		List<Image> savedImages = imageRepo.saveAll(imagesToSave);
		
		// تحديث ال downloadurls بتاع كل الصور 
		for (Image savedImage : savedImages) {
			savedImage.setDownloadUrl("/api/v1/images/"+savedImage.getId());
		}
		imageRepo.saveAll(savedImages);
		return mapper.toListResponse(savedImages);
	}
	
	@Override
	public ImageDto getImageMetaData(Long imageId) throws IOException {
		Image image = imageRepo.findById(imageId).orElseThrow(()->new RecordNotFoundException("Image not found"));
		return mapper.toDto(image);
	}
	@Override
	public Resource getImageResource(Long imageId) throws IOException {
		ImageDto dto = getImageMetaData(imageId);
		return storageService.getFileResource(dto.getFilePath());
	}
	
	private void validateImage(MultipartFile file) {
		if (file.isEmpty()) {
			throw new IllegalArgumentException("File is empty");
		}
		
		String mimeType = file.getContentType();
		if(mimeType == null || !properties.getAllowedMimeTypes().contains(mimeType)) {
			throw new IllegalArgumentException("Invalid mime type");
		}
	}
	@Transactional
	@Override
	public void deleteImage(Long id) throws IOException {
		Image image = imageRepo.findById(id).orElseThrow(()->new RecordNotFoundException("Image not found"));
		Path fileToDelete = Paths.get(properties.getBasePath()).resolve(image.getFilePath());
		Files.deleteIfExists(fileToDelete);
		imageRepo.delete(image);
		
	}
	
	@Transactional
	@Override
	public ImageDto updateImage(MultipartFile file, Long imageId) throws IOException {
		Image image = imageRepo.findById(imageId).orElseThrow(()->new RecordNotFoundException("Image not found"));
		validateImage(file);
		
		Path oldFile = Paths.get(properties.getBasePath()).resolve(image.getFilePath());
	    Files.deleteIfExists(oldFile);
	    
		String storagePath;
		try(InputStream input = file.getInputStream()){
			storagePath = storageService.storeFile(input, file.getOriginalFilename());
		}
		image.setFileName(file.getOriginalFilename());
		image.setFilePath(storagePath);
		image.setFileType(file.getContentType());
		image.setImage(file.getBytes());
		image.setDownloadUrl("/api/v1/images/"+imageId);
		Image savedImage = imageRepo.save(image);
		log.info("Product :",savedImage.getProduct());
		ImageDto dto =mapper.toDto(savedImage);
		return dto;
	}
	@Override
	public List<ImageDto> getImageByProductId(Long productId) {
		List<Image> images = imageRepo.findByProductId(productId);
		return mapper.toListDto(images);
	}
}
