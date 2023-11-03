package com.isttmicroservice.smsantispam.entity;

import java.util.Date;
import java.util.List;
import java.util.Scanner;

import javax.persistence.*;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.isttmicroservice.smsantispam.dto.MessageResultDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@Entity
@Table(name = "sms_messages")
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
public class Message  {
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Id
	private String sms_id;
	
	@NonNull
	@JoinColumn(name = "sender")
	@Column(name = "sender")
//	@ManyToOne(fetch = FetchType.LAZY)
	private String sender;
	@NonNull
	private String receiver;
	
	private String hotkeys;
	
	@Column(name = "analytics_score")
	private float score;
	
	@NonNull
	private String ip_address;

	private String sms_content;
	
	private String sms_type;
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Ho_Chi_Minh")
	@Column(name = "sms_timestamp")
    private Date timeStamps;
	


}
