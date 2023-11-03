package com.isttmicroservice.smsantispam.dto;

import java.util.List;

import com.isttmicroservice.smsantispam.entity.FilterContactLog;
import com.isttmicroservice.smsantispam.entity.FilterFrequencyLog;
import com.isttmicroservice.smsantispam.entity.FilterHotkeysLog;
import com.isttmicroservice.smsantispam.entity.FilterLengthLog;
import com.isttmicroservice.smsantispam.entity.FilterSimilarityLog;
import com.isttmicroservice.smsantispam.entity.FilterSpamwordLog;
import com.isttmicroservice.smsantispam.entity.FilterURLLog;
import com.isttmicroservice.smsantispam.entity.MySQLLog;
import com.isttmicroservice.smsantispam.entity.SystemLog;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponeTestFilterDTO {
	
	private String data;
    private boolean success;
    private int sms_type;
    private String error;
    private String message;
    private SystemLog systemLog;
   
    private List<FilterContactLog> filterContactLogs;
    
    private List<FilterFrequencyLog> filterFrequencyLogs;
   
    private List<FilterHotkeysLog> filterHotkeysLogs;
    private List<FilterLengthLog> filterLengthLogs;
    private List<FilterSimilarityLog> filterSimilarityLogs;
    private List<FilterSpamwordLog> filterSpamwordLogs;
    private List<MySQLLog> mySQLLogs;
    private List<FilterURLLog> filterURLLogs;
    

}
