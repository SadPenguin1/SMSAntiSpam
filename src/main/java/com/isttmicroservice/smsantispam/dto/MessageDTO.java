package com.isttmicroservice.smsantispam.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class MessageDTO {
	@NonNull
	private String sms_id;
	
	private Integer id;
	
	private String sender;
	
	private String receiver;
	
	private String ip_address;

	private String sms_content;
	
	private String sms_type;
	
	private float score;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Ho_Chi_Minh")
    private Date timeStamps;
	
//	private MessageResultDTO sms_result;

}