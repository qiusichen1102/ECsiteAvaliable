package com.example.demo.service;

import java.util.List;

import com.example.demo.bean.Category;

public interface CategoryService {
	//list
	public List<Category> searchCategoryList();

	//detail
	public Category getDetailCategory(String category_id);

	//search
	public List<Category> searchNameList(String keyword);

	//delete
	public void deleteCategory(String category_id);

	//add
	public void addCategory(Category category);

	//update
	public void updateCategory(Category category);
}
