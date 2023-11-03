package com.isttmicroservice.smsantispam.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@Entity
@Table(name = "term_sms_code")
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
public class Code {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String code;
	@Column(name = "code_name")
	private String codeName;

	private String country;

	@JoinColumn(name = "code_deck_id")
	@ManyToOne(fetch = FetchType.LAZY)
	private CodeDeck codeDeck;


	@JoinColumn(name = "created_by")
	@ManyToOne(fetch = FetchType.LAZY)
	private User createdBy;

	@CreatedDate
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_at")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Ho_Chi_Minh")
	private Date createdAt;

	@JoinColumn(name = "updated_by")
	@ManyToOne(fetch = FetchType.LAZY)
	private User updatedBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updated_at")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Ho_Chi_Minh")
	private Date updatedAt;

}
