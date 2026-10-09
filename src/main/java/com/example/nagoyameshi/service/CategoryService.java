package com.example.nagoyameshi.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.nagoyameshi.entity.Category;
import com.example.nagoyameshi.form.CategoryForm;
import com.example.nagoyameshi.repository.CategoryRepository;

@Service
public class CategoryService {
	private final CategoryRepository categoryRepository;
	
	public CategoryService(CategoryRepository categoryRepository) {
		this.categoryRepository = categoryRepository;
	}
	
	@Transactional
	public void create(CategoryForm categoryForm) {
		Category category = new Category();
		category.setName(categoryForm.getName());
		categoryRepository.save(category);
	}
	
	@Transactional
	public void update(CategoryForm categoryForm) {
		Category category = categoryRepository.getReferenceById(categoryForm.getId());		
		category.setName(categoryForm.getName());
		categoryRepository.save(category);
	}
	
	@Transactional
	public void delete(Integer id) {
		categoryRepository.deleteById(id);
	}
}
