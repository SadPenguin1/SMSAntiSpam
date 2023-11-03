package com.isttmicroservice.smsantispam.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.isttmicroservice.smsantispam.entity.FilterContactLog;
import com.isttmicroservice.smsantispam.entity.FilterFrequencyLog;
import com.isttmicroservice.smsantispam.entity.SystemLog;

@Repository
public interface FrequenceLogRepo extends JpaRepository<FilterFrequencyLog,Integer> {

    @Query("SELECT u FROM FilterFrequencyLog u WHERE u.logId = :x ")
    List<FilterFrequencyLog> findByLogId(@Param("x")String logId);
}
