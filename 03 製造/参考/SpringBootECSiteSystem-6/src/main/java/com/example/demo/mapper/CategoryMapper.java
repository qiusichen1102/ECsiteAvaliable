package com.example.demo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.demo.bean.Category;

@Mapper
public interface CategoryMapper {
	//list
	public List<Category> searchCategoryList();

	//detail
	public Category detalCategory(String category_id);

	//search
	public List<Category> search_name(@Param("keyword")String keyword);

	//delete
	public void deleteCategory(String category_id);

	//add
	public void addCategory(Category category);

	//update
	public void updateCategory(Category category);
}
