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
@Table(name = "filter_contact_log")
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
public class FilterContactLog  {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name ="log_id" )
	private String logId;
	
	@Column(name ="level" )
	private String level;
	
	@Column(name ="messages" )
	private String messages;


	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Ho_Chi_Minh")
	@Column(name = "created_at")
    private Date createdAt;
	

//	@ManyToOne(fetch = FetchType.LAZY)
//	@JoinColumn(name = "system_log_id")
//	private SystemLog systemLog;
	
}
