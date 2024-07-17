package com.designershop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.designershop.entities.UserProfile;

@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile, String> {

	@Query(value = "SELECT * FROM userprofile ORDER BY userid DESC LIMIT 1", nativeQuery = true)
	UserProfile findMaxUserId();
	
	@Query(value = "SELECT * FROM userprofile WHERE userid =:userId", nativeQuery = true)
	UserProfile findByUserId(@Param("userId") String userId);
}
