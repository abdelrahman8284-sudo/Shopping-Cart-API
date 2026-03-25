package com.abdelrahman.shoppingcart.mappers;

import java.util.List;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.abdelrahman.shoppingcart.dtos.requests.ProductRequest;
import com.abdelrahman.shoppingcart.dtos.responses.ProductResponse;
import com.abdelrahman.shoppingcart.models.Product;

@Mapper(componentModel = "spring",uses = ImageMapper.class)
public interface ProductMapper {

	@Mappings({
		@Mapping(target = "id",ignore = true),
		@Mapping(target = "category",ignore=true)})
	Product toEntity(ProductRequest request);
	@Mappings({
		@Mapping(target = "id",ignore = true),
		@Mapping(target = "category",ignore=true)})
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE) //null values ميحسبهاش null يحسب القيم المتخزنة عشان لو بعت 
	Product toEntity(ProductRequest request,@MappingTarget Product current);
	
	@Mappings({@Mapping(source="category.id",target = "categoryId")})
	ProductResponse toDto(Product product);
	
	List<ProductResponse> toListDto(List<Product> products);
	

}
