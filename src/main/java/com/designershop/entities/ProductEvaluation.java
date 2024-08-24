package com.designershop.entities;

import java.math.BigDecimal;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "product_evaluation")
public class ProductEvaluation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "evaluation_id", nullable = false)
    private int evaluationId;

    @Column(name = "stars", nullable = false, precision = 3, scale = 2)
    private BigDecimal stars;

    @Column(name = "evaluation", nullable = false, columnDefinition = "TEXT")
    private String evaluation;

    @Column(name = "user_id", nullable = false, length = 10)
    private String userId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id")
    private Product product;

    @Override
    public int hashCode() {
        return Objects.hash(evaluationId);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ProductEvaluation other = (ProductEvaluation) obj;
        return Objects.equals(evaluationId, other.evaluationId);
    }
}
