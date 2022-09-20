package com.ysingh.ecommerce.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ysingh.ecommerce.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
