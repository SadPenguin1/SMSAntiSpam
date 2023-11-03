package com.isttmicroservice.smsantispam.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class JwtRefreshRequestDTO {
   
    private String refreshToken;
    
    private String password;
   
}
