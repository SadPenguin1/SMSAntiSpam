package com.isttmicroservice.smsantispam.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.isttmicroservice.smsantispam.entity.Role;

import lombok.Data;

@Data
public class UserDTO {
private Integer id;
	
	private String name;
	
	private String username;
	@JsonIgnore
	private String password;
	private String address;
	private String phone;

	private String email;
	
	 @JsonManagedReference
	private RoleDTO role;

}
