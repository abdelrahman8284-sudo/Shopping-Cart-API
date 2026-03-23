package com.abdelrahman.shoppingcart.services.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.abdelrahman.shoppingcart.dtos.requests.CategoryRequest;
import com.abdelrahman.shoppingcart.dtos.responses.CategoryResponse;
import com.abdelrahman.shoppingcart.exceptions.AlreadyExistsException;
import com.abdelrahman.shoppingcart.exceptions.RecordNotFoundException;
import com.abdelrahman.shoppingcart.mappers.CategoryMapper;
import com.abdelrahman.shoppingcart.models.Category;
import com.abdelrahman.shoppingcart.repositories.CategoryRepo;
import com.abdelrahman.shoppingcart.services.CategoryService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

	private final CategoryRepo categoryRepo;
	private final CategoryMapper mapper;
	

	@Override
	@Transactional
	public CategoryResponse addCategory(CategoryRequest request) {
		
		if(categoryRepo.existsByName(request.getName()))
			throw new AlreadyExistsException(request.getName()+" already exists!");
		
		Category category = mapper.toEntity(request);
		category.setName(request.getName().toUpperCase());
		
		CategoryResponse response = mapper.toDto(categoryRepo.save(category));
		return response;
	}

	@Override
	public void updateCategory(Long id, CategoryRequest request) {
		Category current = categoryRepo.findById(id).orElseThrow(()->new RecordNotFoundException("Not found Category with id : "+id));
		current.setName(request.getName());
		categoryRepo.save(current);
	}

	@Override
	public void deleteCategory(Long id) {
	    if (!categoryRepo.existsById(id)) {
	        throw new RecordNotFoundException("Category Not found");
	    }
	    categoryRepo.deleteById(id);
	}

	@Override
	public CategoryResponse getCategoryById(Long id) {
		Category Category = categoryRepo.findById(id)
				.orElseThrow(()->new RecordNotFoundException("Category with id:" +id+" not found"));		
		return mapper.toDto(Category);
	}

	@Override
	public CategoryResponse getCategoryByName(String name) {
		Category category = categoryRepo.findByNameIgnoreCase(name).orElseThrow(()->new RecordNotFoundException("Not found category with name :"+name));
		return mapper.toDto(category);
	}

	@Override
	public List<CategoryResponse> getAllCategories() {
		List<Category> categories = categoryRepo.findAllWithProductCount();
		return mapper.toListDto(categories);
	}


	@Override
	public long countCategories() {		
		return categoryRepo.count();
	}

}
