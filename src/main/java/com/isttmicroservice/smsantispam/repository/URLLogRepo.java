package com.isttmicroservice.smsantispam.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.isttmicroservice.smsantispam.entity.FilterURLLog;

@Repository
public interface URLLogRepo extends JpaRepository<FilterURLLog,Integer> {

    @Query("SELECT u FROM FilterURLLog u WHERE u.logId = :x ")
    List<FilterURLLog> findByLogId(@Param("x")String logId);
}
