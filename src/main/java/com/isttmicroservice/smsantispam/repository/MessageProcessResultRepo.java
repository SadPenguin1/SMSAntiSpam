package com.isttmicroservice.smsantispam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.isttmicroservice.smsantispam.entity.MessageProcessResult;

@Repository
public interface MessageProcessResultRepo extends JpaRepository<MessageProcessResult,Integer> {

    @Query("SELECT u FROM MessageProcessResult u WHERE u.message = :x ")
    MessageProcessResult findByMessageId(@Param("x")String messageId);
  
}
