package com.designershop.repositories;

import com.designershop.entities.ProductEvaluation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductEvaluationRepository extends JpaRepository<ProductEvaluation, Long> {

}
