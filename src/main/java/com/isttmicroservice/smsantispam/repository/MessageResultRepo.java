package com.isttmicroservice.smsantispam.repository;
import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.isttmicroservice.smsantispam.dto.StatisticData;
import com.isttmicroservice.smsantispam.entity.MessageResult;

public interface MessageResultRepo extends JpaRepository<MessageResult,Integer> {
	@Query(value = "SELECT time_stamps AS timeStamps, " +
            "CAST(SUM(ham) AS SIGNED) AS totalHam, " +
            "CAST(SUM(spam) AS SIGNED) AS totalSpam, " +
            "CAST(SUM(suspicious) AS SIGNED) AS totalSuspicious " +
            "FROM (" +
            "    SELECT DATE_FORMAT(time_stamps, '%Y-%m-%d %H:%m:%s') AS time_stamps, " +
            "           CAST(SUM(ham) AS SIGNED) AS ham, " +
            "           CAST(SUM(spam) AS SIGNED) AS spam, " +
            "           CAST(SUM(suspicious) AS SIGNED) AS suspicious " +
            "    FROM sms_result " +
            "    WHERE time_stamps >= :startDate AND time_stamps < :endDate " +
            "    GROUP BY time_stamps" +
            ") AS subquery " +
            "GROUP BY time_stamps " +
            "ORDER BY time_stamps", nativeQuery = true)
	 		List<Object[]> getSecondlySum(@Param("startDate") Date startDate, @Param("endDate") Date endDate);
	
	@Query(value = "SELECT time_stamps AS timeStamps, " +
            "CAST(SUM(ham) AS SIGNED) AS totalHam, " +
            "CAST(SUM(spam) AS SIGNED) AS totalSpam, " +
            "CAST(SUM(suspicious) AS SIGNED) AS totalSuspicious " +
            "FROM (" +
            "    SELECT DATE_FORMAT(time_stamps, '%Y-%m-%d %H:00:00') AS time_stamps, " +
            "           CAST(SUM(ham) AS SIGNED) AS ham, " +
            "           CAST(SUM(spam) AS SIGNED) AS spam, " +
            "           CAST(SUM(suspicious) AS SIGNED) AS suspicious " +
            "    FROM sms_result " +
            "    WHERE time_stamps >= :startDate AND time_stamps < :endDate " +
            "    GROUP BY time_stamps" +
            ") AS subquery " +
            "GROUP BY time_stamps " +
            "ORDER BY time_stamps", nativeQuery = true)
	 		List<Object[]> getHourlySum(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

	@Query(value = "SELECT time_stamps AS timeStamps, " +
            "CAST(SUM(ham) AS SIGNED) AS totalHam, " +
            "CAST(SUM(spam) AS SIGNED) AS totalSpam, " +
            "CAST(SUM(suspicious) AS SIGNED) AS totalSuspicious " +
            "FROM (" +
            "    SELECT DATE_FORMAT(time_stamps, '%Y-%m-%d 00:00:00') AS time_stamps, " +
            "           CAST(SUM(ham) AS SIGNED) AS ham, " +
            "           CAST(SUM(spam) AS SIGNED) AS spam, " +
            "           CAST(SUM(suspicious) AS SIGNED) AS suspicious " +
            "    FROM sms_result " +
            "    WHERE time_stamps >= :startDate AND time_stamps < :endDate " +
            "    GROUP BY time_stamps" +
            ") AS subquery " +
            "GROUP BY time_stamps " +
            "ORDER BY time_stamps", nativeQuery = true)
	 		List<Object[]> getDailySum(@Param("startDate") Date startDate, @Param("endDate") Date endDate);
	 	
	 @Query(value = "SELECT time_stamps AS timeStamps, " +
	 	    "CAST(SUM(ham) AS SIGNED) AS totalHam, " +
	 	    "CAST(SUM(spam) AS SIGNED) AS totalSpam, " +
	 	    "CAST(SUM(suspicious) AS SIGNED) AS totalSuspicious " +
	 	    "FROM (" +
	 	    "    SELECT DATE_FORMAT(time_stamps, '%Y-%m-00 00:00:00') AS time_stamps, " +
	 	    "           CAST(SUM(ham) AS SIGNED) AS ham, " +
	 	    "           CAST(SUM(spam) AS SIGNED) AS spam, " +
	 	    "           CAST(SUM(suspicious) AS SIGNED) AS suspicious " +
	 	    "    FROM sms_result " +
	 	    "    WHERE time_stamps >= :startDate AND time_stamps < :endDate " +
	 	    "    GROUP BY time_stamps" +
	 	    ") AS subquery " +
	 	    "GROUP BY time_stamps " +
	 	    "ORDER BY time_stamps", nativeQuery = true)
	 		 List<Object[]> getMonthlySum(@Param("startDate") Date startDate, @Param("endDate") Date endDate);	 		
        
}