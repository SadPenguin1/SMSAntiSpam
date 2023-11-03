package com.isttmicroservice.smsantispam.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.isttmicroservice.smsantispam.entity.CodeDeck;

@Repository
public interface CodeDeckRepo extends JpaRepository<CodeDeck,Integer> {

    @Query("SELECT u FROM CodeDeck u WHERE u.codeDeck LIKE :x ")
    Page<CodeDeck> findByCodeDeck(@Param("x") String s, Pageable pageable);
    
    @Query("SELECT u FROM CodeDeck u WHERE u.codeDeckId = :x ")
    Optional<CodeDeck>  findByCodeDeckId(@Param("x")String codeDeckId);
    
    @Query("SELECT u FROM CodeDeck u WHERE u.codeDeckId = :x ")
   CodeDeck  findByCodeDeck(@Param("x")String codeDeckId);
    
    @Query("SELECT u FROM CodeDeck u WHERE u.codeDeckId IN :ids")
    List<CodeDeck> findAllByIds(@Param("ids") List<String> ids);
}
