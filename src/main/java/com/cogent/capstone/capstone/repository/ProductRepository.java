package com.cogent.capstone.capstone.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cogent.capstone.capstone.entities.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
