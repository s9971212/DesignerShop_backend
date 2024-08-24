package com.designershop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.designershop.entities.ProductLikes;
import com.designershop.entities.ProductLikesId;

@Repository
public interface ProductLikesRepository extends JpaRepository<ProductLikes, ProductLikesId> {

	@Query(value = "SELECT * FROM product_likes WHERE user_id =:userId AND product_id =:productId", nativeQuery = true)
	ProductLikes findByUserIdAndProductId(@Param("userId") String userId, @Param("productId") String productId);

	@Query(value = "SELECT COUNT(*) FROM product_likes WHERE product_id =:productId", nativeQuery = true)
	Long findCountByProductId(@Param("productId") String productId);
}
