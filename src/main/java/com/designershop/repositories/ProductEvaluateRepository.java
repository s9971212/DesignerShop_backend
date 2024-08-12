package com.designershop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.designershop.entities.ProductEvaluate;

@Repository
public interface ProductEvaluateRepository extends JpaRepository<ProductEvaluate, Long> {

}
