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
@Table(name = "sms_process_result")
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
public class MessageProcessResult  {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "sms_id")
	private String message;
	
	@Column(name = "conten_cleaned")
	private String contentCleaned;
	
	@Column(name="frequency_score")
	private Float frequencyScore;
	
	@Column(name="length_score")
	private Float lengthScore;
	
	@Column(name="contact_score")
	private Float contactScore;
	
	@Column(name="contact_found")
	private String contactFound;
	
	@Column(name="url_score")
	private Float urlScore;
	
	@Column(name="url_found")
	private String urlFound;
	
	@Column(name="similarity_score")
	private Float similarityScore;
	
	@Column(name="hotkeys_found")
	private String hotkeysFound;
	
	@Column(name="content_nature")
	private String contentNature;
	
	@Column(name="spamword_score")
	private Float spamwordScore;
	
	@Column(name="spamword_found")
	private String spamwordFound;
	
	@Column(name="sms_nature")
	private String smsNature;
	
	@Column(name="analytics_score")
	private Float analyticsScore;
	
	@Column(name="sms_type")
	private String smsType;
	

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Ho_Chi_Minh")
	@Column(name = "updated_at")
    private Date updatedAt;
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Ho_Chi_Minh")
	@Column(name = "created_at")
    private Date createddAt;
	
}
