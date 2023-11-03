package com.isttmicroservice.smsantispam.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.isttmicroservice.smsantispam.dto.ResponseDTO;
import com.isttmicroservice.smsantispam.entity.MessageProcessResult;
import com.isttmicroservice.smsantispam.repository.MessageProcessResultRepo;

@RestController
@RequestMapping("/message-process-result")
public class MessageProcessResultAPIController {
	@Autowired
	MessageProcessResultRepo messageProcessResultRepo;

	@GetMapping("/{messageId}")
	public ResponseDTO<MessageProcessResult> get(@PathVariable(value = "messageId") String messageId) {
		MessageProcessResult messageProcessResult =  messageProcessResultRepo.findByMessageId(messageId);
		return ResponseDTO.<MessageProcessResult>builder().code(String.valueOf(HttpStatus.OK.value())).data(messageProcessResult)
				.build();
	}



}
