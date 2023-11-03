package com.isttmicroservice.smsantispam.api;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.isttmicroservice.smsantispam.dto.MessageResultDTO;
import com.isttmicroservice.smsantispam.dto.RawData;
import com.isttmicroservice.smsantispam.dto.ResponseDTO;
import com.isttmicroservice.smsantispam.dto.ScoreDTO;
import com.isttmicroservice.smsantispam.dto.SearchDTO;
import com.isttmicroservice.smsantispam.dto.SearchMessageResultDTO;
import com.isttmicroservice.smsantispam.dto.StatisticDTO;
import com.isttmicroservice.smsantispam.dto.StatisticData;
import com.isttmicroservice.smsantispam.repository.MessageResultRepo;
import com.isttmicroservice.smsantispam.service.MessageResultService;

@RestController
@RequestMapping("/message-result")
public class MessageResultAPIController {
    @Autowired
    private MessageResultService messageResultService;

//	public List<ScoreDTO> getMessageScores(Date start, Date end) {
//		List<Object[]> results = messageRepo.countMessagesByScore(start, end);
//		return results.stream().map(result -> new ScoreDTO((Float) result[0], (Long) result[1]))
//				.collect(Collectors.toList());
//	}


//    @PutMapping("/")
//    public ResponseDTO<Void> update(@RequestBody @Valid MessageResultDTO messageResultDTO) throws IOException {
//        messageResultService.update(messageResultDTO);
//        return ResponseDTO.<Void>builder().code(String.valueOf(HttpStatus.OK.value())).build();
//    }

    @GetMapping("/{id}")
    public ResponseDTO<MessageResultDTO> get(@PathVariable(value = "id") int id) {
        return ResponseDTO.<MessageResultDTO>builder().code(String.valueOf(HttpStatus.OK.value()))
                .data(messageResultService.get(id)).build();
    }

    @PostMapping("/search")
    public ResponseDTO<List<MessageResultDTO>> search(@RequestBody @Valid SearchDTO searchDTO) {
        return messageResultService.find(searchDTO);
    }
    
    @PostMapping("/thongke")
    public StatisticData statistic(@RequestBody @Valid SearchMessageResultDTO searchMessageResultDTO)  {
    	return messageResultService.statistic(searchMessageResultDTO);
    }


}
