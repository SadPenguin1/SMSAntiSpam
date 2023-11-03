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
@Table(name = "system_logs")
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
public class SystemLog  {
	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Id
	@Column(name ="log_id")
	private String logId;


//	@OneToOne(fetch = FetchType.LAZY)
	@Column(name = "filter_contact_log_id")
	private String filterContactLog;
	
//
//	@OneToOne(fetch = FetchType.LAZY)
	@Column(name = "filter_frequency_log_id")
	private String filterFrequencyLog;
	

//	@OneToOne(fetch = FetchType.LAZY)
	@Column(name = "filter_hotkeys_log_id")
	private String filterHotkeysLog;
//	
//
//	@OneToOne(fetch = FetchType.LAZY)
	@Column(name = "filter_length_log_id")
	private String filterLengthLog;
//	
//
//	@OneToOne(fetch = FetchType.LAZY)
	@Column(name = "filter_spamword_log_id")
	private String filterSpamwordLog;
//	
//
//	@OneToOne(fetch = FetchType.LAZY)
	@Column(name = "filter_similarity_log_id")
	private String filterSimilarityLog;
//	
//
//	@OneToOne(fetch = FetchType.LAZY)
	@Column(name = "mysql_log_id")
	private String mySQLLog;
	
	@Column(name = "filter_url_log_id")
	private String filterURLLog;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "created_by")
	private User user;


	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Ho_Chi_Minh")
	@Column(name = "updated_at")
    private Date updateAt;
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Ho_Chi_Minh")
	@Column(name = "created_at")
    private Date createdAt;
	
}
