package com.isttmicroservice.smsantispam.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.isttmicroservice.smsantispam.entity.FilterContactLog;
import com.isttmicroservice.smsantispam.entity.FilterLengthLog;
import com.isttmicroservice.smsantispam.entity.SystemLog;

@Repository
public interface LengthLogRepo extends JpaRepository<FilterLengthLog,Integer> {

    @Query("SELECT u FROM FilterLengthLog u WHERE u.logId = :x ")
    List<FilterLengthLog> findByLogId(@Param("x")String logId);
}
