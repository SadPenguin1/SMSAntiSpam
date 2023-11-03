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

import com.isttmicroservice.smsantispam.dto.UrlDTO;
import com.isttmicroservice.smsantispam.dto.ResponseDTO;
import com.isttmicroservice.smsantispam.dto.SearchDTO;
import com.isttmicroservice.smsantispam.service.UrlService;

@RestController
@RequestMapping("/url")
public class UrlAPIController {
    @Autowired
    private UrlService urlService;

    @PostMapping("/")
    public ResponseDTO<UrlDTO> create(@RequestBody @Valid UrlDTO UrlDTO) throws IOException {
        urlService.create(UrlDTO);
        return ResponseDTO.<UrlDTO>builder().code(String.valueOf(HttpStatus.OK.value())).data(UrlDTO).build();
    }

    @PutMapping("/")
    public ResponseDTO<Void> update(@RequestBody @Valid UrlDTO UrlDTO) throws IOException {
        urlService.update(UrlDTO);
        return ResponseDTO.<Void>builder().code(String.valueOf(HttpStatus.OK.value())).build();
    }

    @GetMapping("/{id}")
    public ResponseDTO<UrlDTO> get(@PathVariable(value = "id") int id) {
        return ResponseDTO.<UrlDTO>builder().code(String.valueOf(HttpStatus.OK.value()))
                .data(urlService.get(id)).build();
    }

    @DeleteMapping("/{id}")
    public ResponseDTO<Void> delete(@PathVariable(value = "id") int id) {
        urlService.delete(id);
        return ResponseDTO.<Void>builder().code(String.valueOf(HttpStatus.OK.value())).build();
    }

    @DeleteMapping("/delete/all/{ids}")
    public ResponseDTO<Void> deleteAll(@PathVariable(value = "ids") List<Integer> ids) {
        urlService.deleteAll(ids);
        return ResponseDTO.<Void>builder().code(String.valueOf(HttpStatus.OK.value())).build();
    }


    @PostMapping("/search")
    public ResponseDTO<List<UrlDTO>> search(@RequestBody @Valid SearchDTO searchDTO) {
        return urlService.find(searchDTO);
    }


}
