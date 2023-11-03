package com.isttmicroservice.smsantispam.dto;

import java.math.BigInteger;

import lombok.AllArgsConstructor;
import lombok.Data;
@Data
@AllArgsConstructor
public class RawData {
	private String time_stamps;
	private BigInteger   ham;
	private BigInteger  spam;
	private BigInteger  suspicios;

}
