package com.isttmicroservice.smsantispam.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.isttmicroservice.smsantispam.entity.Hotkey;

@Repository
public interface HotkeyRepo extends JpaRepository<Hotkey,Integer> {

    @Query("SELECT u FROM Hotkey u WHERE u.hotkey LIKE :x ")
    Page<Hotkey> findByHotkeys(@Param("x") String s, Pageable pageable);
    
    @Query("SELECT u FROM Hotkey u WHERE u.hotkey LIKE :x ")
    Optional<Hotkey> findByHotkey(@Param("x") String s);

}
