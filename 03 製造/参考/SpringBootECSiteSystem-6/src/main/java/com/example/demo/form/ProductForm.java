package com.example.demo.form;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;




public class ProductForm {

	@NotNull(message ="{product.error.product_id.notEmpty}")
	private Integer product_id;
	@NotEmpty(message ="{product.error.product_name.notEmpty}")
	private String product_name;
	@NotEmpty(message ="{product.error.product_code.notEmpty}")
	private String product_code;
	@NotNull(message ="{product.error.category_id.notEmpty}")
	private Integer category_id;
	@NotNull(message ="{product.error.manufacture_id.notEmpty}")
	private Integer manufacture_id;
	@NotNull(message ="{product.error.product_price.notEmpty}")
	private Float product_price;
	@NotNull(message ="{product.error.product_quantity.notEmpty}")
	private Integer product_quantity;
	@NotEmpty(message ="{product.error.product_description.notEmpty}")
	private String product_description;

	//private String  product_imagefile;
	@NotNull(message ="{product.error.publication_status.notEmpty}")
	private Integer  publication_status;
	/*	@NotNull(message ="{product.error.product_imagefile.notEmpty}")
		private String product_imagefile;*/

	
public ProductForm() {
		super();
	}
public Integer getProduct_id() {
		return product_id;
	}
	public void setProduct_id(Integer product_id) {
		this.product_id = product_id;
	}


	public String getProduct_name() {
		return product_name;
	}
	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}
	public String getProduct_code() {
		return product_code;
	}
	public void setProduct_code(String product_code) {
		this.product_code = product_code;
	}
	
	/*
	 * public String getProduct_imagefile() { return product_imagefile; } public
	 * void setProduct_imagefile(String product_imagefile) { this.product_imagefile
	 * = product_imagefile; }
	 */
	
	public Integer getCategory_id() {
		return category_id;
	}
	public void setCategory_id(Integer category_id) {
		this.category_id = category_id;
	}
	public Integer getManufacture_id() {
		return manufacture_id;
	}
	public void setManufacture_id(Integer manufacture_id) {
		this.manufacture_id = manufacture_id;
	}
	public Float getProduct_price() {
		return product_price;
	}
	public void setProduct_price(Float product_price) {
		this.product_price = product_price;
	}
	public Integer getProduct_quantity() {
		return product_quantity;
	}
	public void setProduct_quantity(Integer product_quantity) {
		this.product_quantity = product_quantity;
	}
	public Integer getPublication_status() {
		return publication_status;
	}
	public void setPublication_status(Integer publication_status) {
		this.publication_status = publication_status;
	}
	public String getProduct_description() {
		return product_description;
	}
	public void setProduct_description(String product_description) {
		this.product_description = product_description;
	}
//	public byte[] getProduct_image() {
//		return product_image;
//	}
//	public void setProduct_image(byte[] product_image) {
//		this.product_image = product_image;
//	}


public ProductForm(@NotNull(message = "{product.error.product_id.notEmpty}") Integer product_id,
		@NotEmpty(message = "{product.error.product_name.notEmpty}") String product_name,
		@NotEmpty(message = "{product.error.product_code.notEmpty}") String product_code,
		@NotNull(message = "{product.error.category_id.notEmpty}") Integer category_id,
		@NotNull(message = "{product.error.manufacture_id.notEmpty}") Integer manufacture_id,
		@NotNull(message = "{product.error.product_price.notEmpty}") Float product_price,
		@NotNull(message = "{product.error.product_quantity.notEmpty}") Integer product_quantity,
		@NotEmpty(message = "{product.error.product_description.notEmpty}") String product_description,

		@NotNull(message = "{product.error.publication_status.notEmpty}") Integer publication_status) {
	super();
	this.product_id = product_id;
	this.product_name = product_name;
	this.product_code = product_code;
	this.category_id = category_id;
	this.manufacture_id = manufacture_id;
	this.product_price = product_price;
	this.product_quantity = product_quantity;
	this.product_description = product_description;
	this.publication_status = publication_status;
}

	/**
	 *
	 */



}
