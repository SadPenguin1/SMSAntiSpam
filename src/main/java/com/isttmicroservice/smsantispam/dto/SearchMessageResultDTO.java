package com.isttmicroservice.smsantispam.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@AllArgsConstructor
@EqualsAndHashCode
public class SearchMessageResultDTO {
	
	private String type;
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String startDate ;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String endDate;



}
