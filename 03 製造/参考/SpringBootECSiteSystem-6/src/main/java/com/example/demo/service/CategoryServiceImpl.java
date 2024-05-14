package com.example.demo.service;

import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import com.example.demo.bean.Category;
import com.example.demo.mapper.CategoryMapper;

@Service
public class CategoryServiceImpl {
	@Resource
	CategoryMapper categoryMapper;

	//list
	public List<Category> searchCategoryList(){
		List<Category> categoryList = categoryMapper.searchCategoryList();
		return categoryList;
	}

	//detail
	public Category getDetailCategory(String category_id) {
		Category categoryList = categoryMapper.detalCategory(category_id);
				return categoryList;
	}

	//search
	public List<Category> searchNameList(@Param("keyword")String keyword) {
		List<Category> nameList = categoryMapper.search_name(keyword);
		return nameList;
	}

	//delete
	public void deleteCategory(String category_id) {
		categoryMapper.deleteCategory(category_id);
	}

	//add
	public void addCategory(Category category) {
		categoryMapper.addCategory(category);
	}

	public void updateCategory(Category category) {
		categoryMapper.updateCategory(category);

	}
}
