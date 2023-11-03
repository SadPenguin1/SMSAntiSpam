package com.isttmicroservice.smsantispam.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.isttmicroservice.smsantispam.entity.AuditLog;
import com.isttmicroservice.smsantispam.entity.Code;
import com.isttmicroservice.smsantispam.entity.CodeDeck;

@Repository
public interface AuditLogRepo extends JpaRepository<AuditLog,Integer> {

    @Query("SELECT u FROM AuditLog u WHERE u.logMessage LIKE :x ")
    Page<AuditLog> find(@Param("x") String s, Pageable pageable);
   
}
