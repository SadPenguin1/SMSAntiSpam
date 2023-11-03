package com.isttmicroservice.smsantispam.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
public class RoleDTO {
    private Integer id;

    private String role;
    @JsonBackReference
    private UserDTO user;
    
    
}
