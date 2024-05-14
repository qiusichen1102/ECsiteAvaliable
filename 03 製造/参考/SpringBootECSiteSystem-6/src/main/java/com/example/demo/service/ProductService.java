package com.example.demo.service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.bean.Product;
import com.example.demo.mapper.ProductMapper;

@Service
public class ProductService {

	@Resource
	ProductMapper productMapper;
	

		public List<Product> searchProductList(){
			List<Product> productlist = productMapper.searchProductList();
			return productlist;



	}
		
		public Product getProductByProduct_id(String product_id) {
			Product engineerList = productMapper.getProductByProduct_id(product_id);
			return engineerList;
		}
		
		
		public List<Product> searchProduct(@Param("keyword") String keyword){
			List<Product> product =productMapper.searchProduct(keyword);
			return product;
		}
		//check
		public void ProductList_update(Product product) {
			productMapper.product_update(product);
		}


		public void deleteProduct(String product_id) {
			productMapper.deleteProduct(product_id);
		}

		public void insertProduct(Product product) {
			productMapper.insertProduct(product);
		}

//		public void updateProduct(Product product) {
//			productMapper.updateProduct(product);
//		}

		//details
		public Product searchProductInfoByID(String product_id) {
			Product productinfo =productMapper.searchProductInfoByID(product_id);
			return productinfo;
		};

	    public static void saveFile(String uploadDir, String fileName,
	            MultipartFile multipartFile) throws IOException {
	        Path uploadPath = Paths.get(uploadDir);
	        
	         System.out.println(uploadDir);
	         
	        if (!Files.exists(uploadPath)) {
	            Files.createDirectories(uploadPath);
		         System.out.println(uploadPath);
	        }
	         
	        try (InputStream inputStream = multipartFile.getInputStream()) {
	            Path filePath = uploadPath.resolve(fileName);
	            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
	        } catch (IOException ioe) {        
	            throw new IOException("Could not save image file: " + fileName, ioe);
	        }      
	    }







}
