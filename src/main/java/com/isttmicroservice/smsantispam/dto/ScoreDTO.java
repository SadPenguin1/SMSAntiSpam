package com.isttmicroservice.smsantispam.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
@Data
@AllArgsConstructor
public class ScoreDTO {
	
	private float score;
	private Long messageCount;
	
}
