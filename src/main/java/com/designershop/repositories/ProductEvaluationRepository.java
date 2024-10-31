package com.designershop.repositories;

import com.designershop.entities.ProductEvaluations;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductEvaluationRepository extends JpaRepository<ProductEvaluations, Long> {

}
