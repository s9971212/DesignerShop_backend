package com.designershop.repositories;

import com.designershop.entities.ProductEvaluation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Ivan Wang
 * @date 2024/12/22
 * @version 1.0
 */
@Repository
public interface ProductEvaluationRepository extends JpaRepository<ProductEvaluation, Long> {

}
