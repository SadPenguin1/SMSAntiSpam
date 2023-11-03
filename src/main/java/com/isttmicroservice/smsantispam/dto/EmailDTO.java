package com.isttmicroservice.smsantispam.dto;

import lombok.Data;

@Data
public class EmailDTO {
	 	private String from = "nguyenmanhdung01122000@gmail.com" ;
	    private String to;
	    private String toName;
	    private String subject = "Quên mật khẩu ";
	    private String content ;

}
