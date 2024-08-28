package com.designershop.repositories;

import com.designershop.entities.Cart;
import com.designershop.entities.Product;
import com.designershop.entities.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    @Query(value = "SELECT * FROM cart WHERE user_id =:userId", nativeQuery = true)
    Cart findByUserId(@Param("userId") String userId);
}
