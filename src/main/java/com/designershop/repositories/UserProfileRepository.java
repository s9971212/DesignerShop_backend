package com.designershop.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.designershop.entities.UserProfile;

@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile, String> {

	@Query(value = "SELECT * FROM userprofile WHERE account =:account OR email =:email OR phoneno =:phoneNo", nativeQuery = true)
	List<UserProfile> findByAccountOrEmailOrPhoneNo(@Param("account") String account, @Param("email") String email,
			@Param("phoneNo") String phoneNo);

	@Query(value = "SELECT * FROM userprofile ORDER BY userid DESC LIMIT 1", nativeQuery = true)
	UserProfile findMaxUserId();

	@Query(value = "SELECT * FROM userprofile WHERE userid =:userId AND account =:account", nativeQuery = true)
	UserProfile findByUserIdAndAccount(@Param("userId") String userId, @Param("account") String account);
}
