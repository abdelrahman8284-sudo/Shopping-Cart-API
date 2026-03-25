package com.abdelrahman.shoppingcart.mappers;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.abdelrahman.shoppingcart.dtos.requests.ImageDto;
import com.abdelrahman.shoppingcart.dtos.responses.ImageResponse;
import com.abdelrahman.shoppingcart.models.Image;

@Mapper(componentModel = "spring")
public interface ImageMapper {

	@Mapping(target = "image", ignore = true)
	@Mapping(target = "product", ignore = true)
	Image toEntity(ImageDto dto);
	
	@Mapping(target = "productId",
	         expression = "java(image.getProduct() != null ? image.getProduct().getId() : null)")
	ImageDto toDto(Image image);
	
	ImageResponse toResponse(Image image);
	
	List<ImageResponse> toListResponse(List<Image> images);
	
	List<ImageDto> toListDto(List<Image> images);
}
