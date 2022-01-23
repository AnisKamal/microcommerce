package com.ecommerce.microcommerce.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.microcommerce.model.Product;

@Repository 
public interface ProductDao extends JpaRepository<Product, Integer>{
	
	public Product findById(int id);
	List <Product> findByPrixGreaterThan(int prixLimit);
	
	//public List<Product>findAll();
	//public Product save(Product product);
}
