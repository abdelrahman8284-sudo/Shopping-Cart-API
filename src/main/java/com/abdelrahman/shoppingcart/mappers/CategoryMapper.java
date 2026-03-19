package com.abdelrahman.shoppingcart.mappers;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.abdelrahman.shoppingcart.dtos.requests.CategoryRequest;
import com.abdelrahman.shoppingcart.dtos.responses.CategoryResponse;
import com.abdelrahman.shoppingcart.models.Category;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

	Category toEntity(CategoryRequest request);
	@Mapping(target = "productCount", expression = "java(category.getProducts() != null ? (long) category.getProducts().size() : 0L)")
	CategoryResponse toDto(Category category);
	
	List<CategoryResponse> toListDto(List<Category> categories);
}
