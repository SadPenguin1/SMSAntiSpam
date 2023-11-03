package com.isttmicroservice.smsantispam.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@SuperBuilder
public abstract class CreateAuditable {

	@CreatedBy
	@JoinColumn(name = "create_by")
	@ManyToOne(fetch = FetchType.LAZY)
	private User createBy;
	
	@CreatedDate
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_date")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Ho_Chi_Minh")
	private Date createDate;
	
	@JoinColumn(name = "update_by")
	@ManyToOne(fetch = FetchType.LAZY)
	private User updateBy;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "update_date")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Ho_Chi_Minh")
	private Date updateDate;
	
}
