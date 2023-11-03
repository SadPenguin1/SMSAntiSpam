package com.isttmicroservice.smsantispam.api;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.isttmicroservice.smsantispam.dto.MessageDTO;
import com.isttmicroservice.smsantispam.dto.ResponseDTO;
import com.isttmicroservice.smsantispam.dto.ResponseSearchMessageDTO;
import com.isttmicroservice.smsantispam.dto.SearchDTO;
import com.isttmicroservice.smsantispam.service.MessageService;

@RestController
@RequestMapping("/message")
public class MessageAPIController {
	@Autowired
	private MessageService messageService;

	@GetMapping("/{id}")
	public ResponseDTO<MessageDTO> get(@PathVariable(value = "id") int id) {
		return ResponseDTO.<MessageDTO>builder().code(String.valueOf(HttpStatus.OK.value()))
				.data(messageService.get(id)).build();
	}

	@DeleteMapping("/{id}")
	public ResponseDTO<Void> delete(@PathVariable(value = "id") int id) {
		messageService.delete(id);
		return ResponseDTO.<Void>builder().code(String.valueOf(HttpStatus.OK.value())).build();
	}

	@DeleteMapping("/delete/all/{ids}")
	public ResponseDTO<Void> deleteAll(@PathVariable(value = "ids") List<Integer> ids) {
		messageService.deleteAll(ids);
		return ResponseDTO.<Void>builder().code(String.valueOf(HttpStatus.OK.value())).build();
	}

	@PostMapping("/statistic")
	public ResponseSearchMessageDTO<List<MessageDTO>> statistic(@RequestBody @Valid SearchDTO searchDTO) {
		return messageService.statistic(searchDTO);
	}
	
	@PostMapping("/score")
	public ResponseSearchMessageDTO<List<MessageDTO>> score(@RequestBody @Valid SearchDTO searchDTO) {
		return messageService.score(searchDTO);
	}

	@PostMapping("/search")
	public ResponseDTO<List<MessageDTO>> search(@RequestBody @Valid SearchDTO searchDTO) {
		return messageService.find(searchDTO);
	}

}
