package com.isttmicroservice.smsantispam.dto;

import java.util.Date;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class CodeDeckDTO  {

	private Integer id;
	
	private String codeDeckId;

	private String codeDeck;

	private UserDTO createdBy;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Ho_Chi_Minh")
	private Date createdAt;

	private UserDTO updatedBy;
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Ho_Chi_Minh")
	private Date updatedAt;
	
	

}
