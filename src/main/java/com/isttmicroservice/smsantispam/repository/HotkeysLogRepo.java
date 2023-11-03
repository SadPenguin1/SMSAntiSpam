package com.isttmicroservice.smsantispam.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.isttmicroservice.smsantispam.entity.FilterContactLog;
import com.isttmicroservice.smsantispam.entity.FilterHotkeysLog;
import com.isttmicroservice.smsantispam.entity.SystemLog;

@Repository
public interface HotkeysLogRepo extends JpaRepository<FilterHotkeysLog,Integer> {

    @Query("SELECT u FROM FilterHotkeysLog u WHERE u.logId = :x ")
    List<FilterHotkeysLog> findByLogId(@Param("x")String logId);
}
