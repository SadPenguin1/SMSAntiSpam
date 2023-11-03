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
@Table(name = "sms_users")
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
public class MessageUser  {
//	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Id
	@NonNull
	private String sender;
	@NonNull
	@Column(name ="sms_brandname")
	private String branchname;
	
	@Column(name ="reputation_level")
	private String reputationLevel;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Ho_Chi_Minh")
	@Column(name = "updated_at")
    private Date updateAt;
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Ho_Chi_Minh")
	@Column(name = "created_at")
    private Date createdAt;
	
}
