package com.isttmicroservice.smsantispam.dto;


import lombok.Data;
@Data
public class Filters {
	 private FilterConfig filter_spam_word;
     private FilterConfig filter_hotkeys;
     private FilterConfig filter_length;
     private FilterConfig filter_url;
     private FilterConfig filter_contact;
     private FilterConfig filter_similarity;
     private FilterConfig filter_frequency;
     private FilterConfig filter_ip;

}
