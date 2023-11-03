package com.isttmicroservice.smsantispam.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatisticDTO {
	@NonNull
	  @JsonInclude(JsonInclude.Include.NON_NULL)
	  private List<RawData> rawDatas;

}
