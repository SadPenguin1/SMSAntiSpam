package com.isttmicroservice.smsantispam.api;

import java.io.IOException;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.isttmicroservice.smsantispam.dto.CodeDeckDTO;
import com.isttmicroservice.smsantispam.dto.ResponseDTO;
import com.isttmicroservice.smsantispam.dto.SearchDTO;
import com.isttmicroservice.smsantispam.service.CodeDeckService;

@RestController
@RequestMapping("/codedeck")
public class CodeDeckAPIController {
	@Autowired
	private CodeDeckService codeDeckService;

	@PostMapping("/")
	public ResponseDTO<CodeDeckDTO> create(@RequestBody @Valid CodeDeckDTO CodeDeckDTO) throws IOException {
		try {
			codeDeckService.create(CodeDeckDTO);
			return ResponseDTO.<CodeDeckDTO>builder().code(String.valueOf(HttpStatus.OK.value())).data(CodeDeckDTO)
					.build();
		} catch (Exception e) {
			if(CodeDeckDTO.getCodeDeck()==null && CodeDeckDTO.getCreatedBy().getId()==null) {
			return ResponseDTO.<CodeDeckDTO>builder().code(String.valueOf(HttpStatus.OK.value())).data(CodeDeckDTO)
					.message("Thiếu người tạo  và thiết dữ liệu trường codedeck").build();
			}else if (CodeDeckDTO.getCodeDeck()==null){
				return ResponseDTO.<CodeDeckDTO>builder().code(String.valueOf(HttpStatus.OK.value())).data(CodeDeckDTO)
						.message("Thiếu  dữ liệu trường codedeck").build();
			}else {
				return ResponseDTO.<CodeDeckDTO>builder().code(String.valueOf(HttpStatus.OK.value())).data(CodeDeckDTO)
						.message("Thiếu người tạo yêu cầu đăng nhập").build();
			}
		}

	}

	@PutMapping("/")
	public ResponseDTO<Void> update(@RequestBody @Valid CodeDeckDTO CodeDeckDTO) throws IOException {
		codeDeckService.update(CodeDeckDTO);
		return ResponseDTO.<Void>builder().code(String.valueOf(HttpStatus.OK.value())).build();
	}

	@GetMapping("/{id}")
	public ResponseDTO<CodeDeckDTO> get(@PathVariable(value = "id") int id) {
		return ResponseDTO.<CodeDeckDTO>builder().code(String.valueOf(HttpStatus.OK.value()))
				.data(codeDeckService.get(id)).build();
	}

	@DeleteMapping("/{id}")
	public ResponseDTO<Void> delete(@PathVariable(value = "id") String id) {
		codeDeckService.delete(id);
		return ResponseDTO.<Void>builder().code(String.valueOf(HttpStatus.OK.value())).build();
	}

	@DeleteMapping("/delete/all/{ids}")
	public ResponseDTO<Void> deleteAll(@PathVariable(value = "ids") List<String> ids) {
		codeDeckService.deleteAll(ids);
		return ResponseDTO.<Void>builder().code(String.valueOf(HttpStatus.OK.value())).build();
	}

	@PostMapping("/search")
	public ResponseDTO<List<CodeDeckDTO>> search(@RequestBody @Valid SearchDTO searchDTO) {
		return codeDeckService.find(searchDTO);
	}

}
