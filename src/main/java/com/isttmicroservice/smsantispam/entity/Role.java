package com.isttmicroservice.smsantispam.entity;

import lombok.Data;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Data
@Table(name="role")
@Entity
public class Role {
    @Id
    @Column(name = "role_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String role;
    
    @OneToOne
    @JsonBackReference
    private User user;
}

