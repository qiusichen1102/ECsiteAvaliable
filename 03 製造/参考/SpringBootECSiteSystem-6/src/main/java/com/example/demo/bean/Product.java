package com.example.demo.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.annotation.Transient;


@Entity
@Table(name = "empsystem")
public class Product {
	@Id
	private Integer product_id;
	private String product_name;
	private String product_code;
	private Integer category_id;
	private Integer manufacture_id;
	private Float product_price;
	private Integer product_quantity;
	private String product_description;
	private Integer publication_status;
	@Column(nullable = true, length = 255)
	private String product_imagefile;

	
	@Transient
	public String getProductImagePath() {
		if(product_imagefile == null || product_id == null) return null;
		return "/img/" + product_id + "/" + product_imagefile;
	}

	public Product() {
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

	public String getProduct_description() {
		return product_description;
	}

	public void setProduct_description(String product_description) {
		this.product_description = product_description;
	}

	public Integer getPublication_status() {
		return publication_status;
	}

	public void setPublication_status(Integer publication_status) {
		this.publication_status = publication_status;
	}

	public String getProduct_imagefile() {
		return product_imagefile;
	}

	public void setProduct_imagefile(String product_imagefile) {
		this.product_imagefile = product_imagefile;
	}

	public Product(Integer product_id, String product_name, String product_code, Integer category_id,
			Integer manufacture_id, Float product_price, Integer product_quantity, String product_description,
			Integer publication_status) {
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
		//this.product_imagefile = product_imagefile;
	}







}
