package com.isttmicroservice.smsantispam.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.isttmicroservice.smsantispam.entity.SystemLog;

@Repository
public interface SystemLogRepo extends JpaRepository<SystemLog,Integer> {

    @Query("SELECT u FROM SystemLog u WHERE u.logId = :x ")
    SystemLog findByLogId(@Param("x")String logId);
}
