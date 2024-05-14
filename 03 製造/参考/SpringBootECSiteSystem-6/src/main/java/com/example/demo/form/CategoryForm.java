package com.example.demo.form;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class CategoryForm {
	@NotNull(message ="{category.error.category_id.notEmpty}")
	private Integer category_id;

	@NotEmpty(message ="{category.error.category_name.notEmpty}")
	private String category_name;

	@NotEmpty(message ="{category.error.category_description.notEmpty}")
	private String category_description;

	@NotNull(message = "{category.error.category_status.notEmpty}")
	private Integer publication_status;

	public Integer getCategory_id() {
		return category_id;
	}

	public void setCategory_id(Integer category_id) {
		this.category_id = category_id;
	}

	public String getCategory_name() {
		return category_name;
	}

	public void setCategory_name(String category_name) {
		this.category_name = category_name;
	}

	public String getCategory_description() {
		return category_description;
	}

	public void setCategory_description(String category_description) {
		this.category_description = category_description;
	}

	public Integer getPublication_status() {
		return publication_status;
	}

	public void setPublication_status(Integer publication_status) {
		this.publication_status = publication_status;
	}
}
