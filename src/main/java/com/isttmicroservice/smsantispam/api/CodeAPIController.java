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

import com.isttmicroservice.smsantispam.dto.CodeDTO;
import com.isttmicroservice.smsantispam.dto.ResponseDTO;
import com.isttmicroservice.smsantispam.dto.SearchDTO;
import com.isttmicroservice.smsantispam.service.CodeService;

@RestController
@RequestMapping("/code")
public class CodeAPIController {
    @Autowired
    private CodeService codeService;

    @PostMapping("/")
    public ResponseDTO<CodeDTO> create(@RequestBody @Valid CodeDTO codeDTO) throws IOException {
       codeService.create(codeDTO);
        return ResponseDTO.<CodeDTO>builder().code(String.valueOf(HttpStatus.OK.value())).data(codeDTO).build();
    }

    @PutMapping("/")
    public ResponseDTO<Void> update(@RequestBody @Valid CodeDTO codeDTO) throws IOException {
       codeService.update(codeDTO);
        return ResponseDTO.<Void>builder().code(String.valueOf(HttpStatus.OK.value())).build();
    }

    @GetMapping("/{id}")
    public ResponseDTO<CodeDTO> get(@PathVariable(value = "id") int id) {
        return ResponseDTO.<CodeDTO>builder().code(String.valueOf(HttpStatus.OK.value()))
                .data(codeService.get(id)).build();
    }

    @DeleteMapping("/{id}")
    public ResponseDTO<Void> delete(@PathVariable(value = "id") int id) {
       codeService.delete(id);
        return ResponseDTO.<Void>builder().code(String.valueOf(HttpStatus.OK.value())).build();
    }

    @DeleteMapping("/delete/all/{ids}")
    public ResponseDTO<Void> deleteAll(@PathVariable(value = "ids") List<Integer> ids) {
       codeService.deleteAll(ids);
        return ResponseDTO.<Void>builder().code(String.valueOf(HttpStatus.OK.value())).build();
    }


    @PostMapping("/search")
    public ResponseDTO<List<CodeDTO>> search(@RequestBody @Valid SearchDTO searchDTO) {
        return codeService.find(searchDTO);
    }


}
