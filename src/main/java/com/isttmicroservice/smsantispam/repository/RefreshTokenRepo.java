package com.isttmicroservice.smsantispam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.isttmicroservice.smsantispam.entity.RefreshToken;

import java.util.Optional;

public interface RefreshTokenRepo extends JpaRepository<RefreshToken, Integer> {
	
	@Query("select rt from refreshtoken rt where rt.token LIKE :token")
    Optional<RefreshToken> findByToken(@Param("token")String token);

    @Modifying
    @Query("delete from refreshtoken rt where rt.user.id = :uid")
    int deleteByUserId(@Param("uid") int id);

    @Query("select rt from refreshtoken rt where rt.user.id = :uid")
    RefreshToken getRefreshTokenById(@Param("uid") int id);
  
}
