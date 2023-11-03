package com.isttmicroservice.smsantispam.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
@Data
@AllArgsConstructor
public class MessageStatisticDTO {
	
	private String smsType;
	private Long messageCount;
	
}
