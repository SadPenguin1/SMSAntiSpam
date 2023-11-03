package com.isttmicroservice.smsantispam.dto;


import java.math.BigInteger;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatisticData {
	
//		@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
		private String[] timeStamps;
	    private BigInteger[] totalHam;
	    private BigInteger[] totalSpam;
	    private BigInteger[] totalSuspicious;

	    // getters và setters
	

}
