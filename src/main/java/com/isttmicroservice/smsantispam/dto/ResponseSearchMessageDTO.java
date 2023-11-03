package com.isttmicroservice.smsantispam.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import org.springframework.http.HttpStatus;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseSearchMessageDTO<T> {

    @Builder.Default
    private String code = String.valueOf(HttpStatus.OK.value());

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long totalElements;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long numberOfElements;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long totalPages;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<MessageStatisticDTO> messageStatisticDTOs;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<ScoreDTO> scoreDTOs;
 
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;
    
//    @JsonInclude(JsonInclude.Include.NON_NULL)
//    private String token; // Thêm trường token

}
