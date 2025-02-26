package com.designershop.repositories;

import com.designershop.entities.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Ivan Wang
 * @version 1.0
 * @date 2024/12/22
 */
@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile, String> {

    @Query(value = "SELECT * FROM user_profile WHERE account =:account OR email =:account OR phone_no =:account", nativeQuery = true)
    UserProfile findByLogin(@Param("account") String account);

    @Query(value = "SELECT * FROM user_profile WHERE account =:account OR email =:email OR phone_no =:phoneNo", nativeQuery = true)
    List<UserProfile> findByAccountOrEmailOrPhoneNo(@Param("account") String account, @Param("email") String email,
                                                    @Param("phoneNo") String phoneNo);

    @Query(value = "SELECT * FROM user_profile ORDER BY user_id DESC LIMIT 1", nativeQuery = true)
    UserProfile findMaxUserId();

    @Query(value = "SELECT * FROM user_profile WHERE user_id =:userId", nativeQuery = true)
    UserProfile findByUserId(@Param("userId") String userId);

    @Query(value = "SELECT * FROM user_profile WHERE email =:email", nativeQuery = true)
    UserProfile findByEmail(@Param("email") String email);

    @Query(value = "SELECT * FROM user_profile WHERE refresh_hash =:token", nativeQuery = true)
    UserProfile findByRefreshHash(@Param("token") String token);

    @Query(value = "SELECT * FROM user_profile WHERE user_id IN (:userIds)", nativeQuery = true)
    List<UserProfile> findByUserIds(@Param("userIds") List<String> userIds);
}
