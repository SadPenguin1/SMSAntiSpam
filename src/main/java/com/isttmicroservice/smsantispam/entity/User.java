package com.isttmicroservice.smsantispam.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "user")
@EqualsAndHashCode(callSuper = false)
public class User  {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private String name;
	
	private String username;

	private String password;
	
	private String address;
	
	private String phone;

	@Column(name = "email", unique = true)
	private String email;

	@OneToOne(mappedBy = "user",fetch = FetchType.EAGER)
	@JsonManagedReference
	private Role role;


}
