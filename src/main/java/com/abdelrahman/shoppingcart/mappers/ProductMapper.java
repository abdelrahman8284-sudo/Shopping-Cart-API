package com.abdelrahman.shoppingcart.mappers;

import java.util.List;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

import com.abdelrahman.shoppingcart.dtos.requests.ProductRequest;
import com.abdelrahman.shoppingcart.dtos.responses.ProductResponse;
import com.abdelrahman.shoppingcart.models.Image;
import com.abdelrahman.shoppingcart.models.Product;

@Mapper(componentModel = "spring")
public interface ProductMapper {

	@Mappings({
		@Mapping(target = "id",ignore = true),
		@Mapping(target = "category",ignore=true)})
	Product toEntity(ProductRequest request);
	@Mappings({
		@Mapping(target = "id",ignore = true),
		@Mapping(target = "category",ignore=true)})
	Product toEntity(ProductRequest request,@MappingTarget Product current);
	
	@Mappings({@Mapping(source="category.id",target = "categoryId")})
	ProductResponse toDto(Product product);
	
	List<ProductResponse> toListDto(List<Product> products);
	
	@AfterMapping
	default void mapImagesIds(Product product,@MappingTarget ProductResponse dto) {
		if(product.getImages()!=null) {
			dto.setImagesIds(product.getImages().stream().map(Image::getId).toList());
		}
	}
}
