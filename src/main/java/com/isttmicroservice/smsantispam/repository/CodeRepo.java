package com.isttmicroservice.smsantispam.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.isttmicroservice.smsantispam.entity.Code;
import com.isttmicroservice.smsantispam.entity.CodeDeck;

@Repository
public interface CodeRepo extends JpaRepository<Code,Integer> {

    @Query("SELECT u FROM Code u WHERE u.code LIKE :x ")
    Page<Code> findByCode(@Param("x") String s, Pageable pageable);
    
    @Query("SELECT u FROM Code u WHERE u.code LIKE :x AND u.codeDeck.codeDeckId LIKE :codeDeckId ")
    Page<Code> findByCodeDeck(@Param("x") String s,@Param("codeDeckId") String codeDeckId, Pageable pageable);
}
