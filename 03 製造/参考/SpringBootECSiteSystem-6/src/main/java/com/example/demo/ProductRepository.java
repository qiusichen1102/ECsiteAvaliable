package com.example.demo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.bean.Product;

public interface ProductRepository extends JpaRepository<Product,String>{


}
