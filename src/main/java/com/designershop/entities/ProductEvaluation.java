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

/**
 * @author Ivan Wang
 * @version 1.0
 * @date 2024/12/22
 */
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "product_evaluations")
public class ProductEvaluation {

    /**
     * 商品評價ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "evaluation_id", nullable = false)
    private int evaluationId;

    /**
     * 商品評分
     */
    @Column(name = "stars", nullable = false, precision = 3, scale = 2)
    private BigDecimal stars;

    /**
     * 商品評價內容
     */
    @Column(name = "evaluation", columnDefinition = "TEXT")
    private String evaluation;

    /**
     * 創建該商品評價的時間
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Column(name = "created_date", nullable = false)
    private LocalDateTime createdDate;

    /**
     * 上次更新該商品評價的使用者ID
     */
    @Column(name = "updated_user", length = 12)
    private String updatedUser;

    /**
     * 上次更新該商品評價的時間
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Column(name = "updated_date")
    private LocalDateTime updatedDate;

    /**
     * 使用者ID，表示這個商品評價屬於哪一位使用者
     */
    @Column(name = "user_id", nullable = false, length = 12)
    private String userId;

    /**
     * 商品ID，表示這個商品評價屬於哪一個商品
     */
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
