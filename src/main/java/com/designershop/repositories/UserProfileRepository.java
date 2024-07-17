package com.designershop.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.designershop.entities.UserProfile;

@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile, String> {

	@Query(value = "SELECT * FROM userProfile up WHERE up.userId =:userId ORDER BY up.userId", nativeQuery = true)
	UserProfile findByUserId(@Param("userId") String userId);
}
