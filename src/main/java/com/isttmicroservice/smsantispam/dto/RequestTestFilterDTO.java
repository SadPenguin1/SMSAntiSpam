package com.isttmicroservice.smsantispam.dto;

import lombok.Data;
@Data
public class RequestTestFilterDTO {
    private String sender;
    private String sms;
    private Filters filters;
}
