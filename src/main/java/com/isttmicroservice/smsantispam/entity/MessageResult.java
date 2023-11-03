package com.isttmicroservice.smsantispam.entity;

import java.util.Date;
import java.util.List;
import java.util.Scanner;

import javax.persistence.*;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "sms_result")
@EqualsAndHashCode(callSuper = false)
public class MessageResult  {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")    
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name="time_stamps")
	private Date timeStamps;
	
	private long ham;
	private long spam;
	private long suspicious;
	
}
