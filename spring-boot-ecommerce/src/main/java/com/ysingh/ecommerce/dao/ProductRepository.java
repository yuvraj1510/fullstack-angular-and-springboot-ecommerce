package com.ysingh.ecommerce.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.ysingh.ecommerce.entity.Product;

@CrossOrigin
public interface ProductRepository extends JpaRepository<Product, Long> {
	
	Page<Product> findByCategoryId(@Param("id") Long id, Pageable pageable);
	
	Page<Product> findByNameContaining(@Param("name") String name, Pageable pageable);

}
