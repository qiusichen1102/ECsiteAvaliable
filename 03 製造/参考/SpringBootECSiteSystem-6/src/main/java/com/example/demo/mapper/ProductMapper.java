package com.example.demo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.demo.bean.Product;

@Mapper
public interface ProductMapper {

	//list
	public List<Product> searchProductList();
	
	//search by ID
	public List<Product> searchProduct(@Param("keyword") String keyword);

	public void deleteProduct(String product_id);

	public void insertProduct(Product product);

	//public void updateProduct(Product product);

	// update check
		public void product_update(Product product);




		//public Product getProductListByInfoByproduct_id(String product_id);

	//details
	public Product searchProductInfoByID(String product_id);

	public Product getProductByProduct_id(String product_id);
	

}
