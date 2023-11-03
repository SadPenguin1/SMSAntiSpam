package com.isttmicroservice.smsantispam.repository;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.isttmicroservice.smsantispam.dto.MessageStatisticDTO;
import com.isttmicroservice.smsantispam.entity.Message;

public interface MessageRepo extends JpaRepository<Message,Integer> {
        @Query("SELECT u FROM Message u WHERE u.sms_content LIKE :x ")
        Page<Message> findByContent(@Param("x") String s, Pageable pageable);
        
        @Query("SELECT u FROM Message u  WHERE (u.sms_content LIKE :x OR u.receiver LIKE :x OR u.sender LIKE :x) AND u.sms_type LIKE :type ")
        Page<Message> findByContentAndType(@Param("x") String value,@Param("type") String type, Pageable pageable);

    	@Query("SELECT u FROM Message u WHERE (u.sms_content LIKE :x OR u.receiver LIKE :x OR u.sender LIKE :x) AND u.sms_type LIKE :type AND u.timeStamps >= :start and u.timeStamps <= :end")
    	Page<Message> searchByDateAndType(@Param("x") String value,@Param("type") String type, @Param("start") Date start, @Param("end") Date end, Pageable pageable);
    	
    	@Query("SELECT u FROM Message u  WHERE (u.sms_content LIKE :x OR u.receiver LIKE :x OR u.sender LIKE :x) AND  u.timeStamps >= :start and u.timeStamps <= :end")
    	Page<Message> searchByDate(@Param("x") String value, @Param("start") Date start, @Param("end") Date end, Pageable pageable);
    	
    	@Query("SELECT u FROM Message u WHERE u.sms_content LIKE :x OR u.receiver LIKE :x OR u.sender LIKE :x")
        Page<Message> findByValue(@Param("x") String s, Pageable pageable);
    	
    	@Query("SELECT m.sms_type AS smsType, COUNT(m) AS messageCount FROM Message m WHERE m.timeStamps >= :start AND m.timeStamps <= :end GROUP BY m.sms_type")
    	List<Object[]> countMessagesByType( @Param("start") Date start, @Param("end") Date end);
    	
    	@Query("SELECT m.score AS smsType, COUNT(m) AS messageCount FROM Message m WHERE m.timeStamps >= :start AND m.timeStamps <= :end GROUP BY m.score ORDER BY m.score ASC")
    	List<Object[]> countMessagesByScore( @Param("start") Date start, @Param("end") Date end);
    	

}