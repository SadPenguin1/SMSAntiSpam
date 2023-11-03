package com.isttmicroservice.smsantispam.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.isttmicroservice.smsantispam.entity.Url;

@Repository
public interface UrlRepo extends JpaRepository<Url,Integer> {

    @Query("SELECT u FROM Url u WHERE u.url LIKE :x ")
    Page<Url> findByUrl(@Param("x") String s, Pageable pageable);


}
