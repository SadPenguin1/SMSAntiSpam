package com.isttmicroservice.smsantispam.dto;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
@Data
public class MessageResultDTO {
	
	private Integer id;
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")    
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	 private Date timeStamps;
	
	private long ham;
	private long spam;
	private long suspicious;
	
}
