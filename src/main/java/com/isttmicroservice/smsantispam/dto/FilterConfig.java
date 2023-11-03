package com.isttmicroservice.smsantispam.dto;

import java.util.Map;

import lombok.Data;
@Data
public class FilterConfig {
	   private boolean status;
       private Map<String, Integer> config;

}
