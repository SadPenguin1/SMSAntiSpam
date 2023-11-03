package com.isttmicroservice.smsantispam.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.isttmicroservice.smsantispam.entity.CodeDeck;

import lombok.Data;

@Data
public class CodeDTO  {

	private Integer id;

	private String code;
	
	private String codeName;
	
	private String country;

	private UserDTO createBy;
	
	private CodeDeckDTO codeDeckDTO;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Ho_Chi_Minh")
	private Date createAt;

	private UserDTO updateBy;
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Ho_Chi_Minh")
	private Date updateAt;
	
	

}
