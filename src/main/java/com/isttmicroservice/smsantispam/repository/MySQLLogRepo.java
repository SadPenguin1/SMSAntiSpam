package com.isttmicroservice.smsantispam.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.isttmicroservice.smsantispam.entity.FilterContactLog;
import com.isttmicroservice.smsantispam.entity.MySQLLog;
import com.isttmicroservice.smsantispam.entity.SystemLog;

@Repository
public interface MySQLLogRepo extends JpaRepository<MySQLLog,Integer> {

    @Query("SELECT u FROM MySQLLog u WHERE u.logId = :x ")
    List<MySQLLog> findByLogId(@Param("x")String logId);
}
