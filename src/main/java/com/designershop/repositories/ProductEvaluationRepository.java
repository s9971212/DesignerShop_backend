package com.designershop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.designershop.entities.ProductEvaluation;

@Repository
public interface ProductEvaluationRepository extends JpaRepository<ProductEvaluation, Long> {

}
