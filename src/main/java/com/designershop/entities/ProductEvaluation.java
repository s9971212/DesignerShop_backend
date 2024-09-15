package com.designershop.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

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

    @Column(name = "evaluation", columnDefinition = "TEXT")
    private String evaluation;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Column(name = "created_date", nullable = false)
    private LocalDateTime createdDate;

    @Column(name = "updated_user", length = 12)
    private String updatedUser;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Column(name = "updated_date")
    private LocalDateTime updatedDate;

    @Column(name = "user_id", nullable = false, length = 12)
    private String userId;

    @Column(name = "product_id", nullable = false)
    private int productId;

    @Override
    public int hashCode() {
        return Objects.hash(evaluationId, userId, productId);
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
        return Objects.equals(evaluationId, other.evaluationId) && Objects.equals(userId, other.userId) && Objects.equals(productId, other.productId);
    }

    @PrePersist
    @PreUpdate
    public void handleEmptyStrings() {
        if (StringUtils.isBlank(this.evaluation)) {
            this.evaluation = null;
        }
    }
}
