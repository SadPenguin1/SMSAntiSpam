package com.isttmicroservice.smsantispam.api;

import java.io.IOException;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.isttmicroservice.smsantispam.dto.HotkeyDTO;
import com.isttmicroservice.smsantispam.dto.ResponseDTO;
import com.isttmicroservice.smsantispam.dto.SearchDTO;
import com.isttmicroservice.smsantispam.service.HotkeyService;

@RestController
@RequestMapping("/hotkey")
public class HotkeyAPIController {
    @Autowired
    private HotkeyService hotkeyService;

    @PostMapping("/")
    public ResponseDTO<HotkeyDTO> create(@RequestBody @Valid HotkeyDTO HotkeyDTO) throws IOException {
        hotkeyService.create(HotkeyDTO);
        return ResponseDTO.<HotkeyDTO>builder().code(String.valueOf(HttpStatus.OK.value())).data(HotkeyDTO).build();
    }

    @PutMapping("/")
    public ResponseDTO<Void> update(@RequestBody @Valid HotkeyDTO HotkeyDTO) throws IOException {
        hotkeyService.update(HotkeyDTO);
        return ResponseDTO.<Void>builder().code(String.valueOf(HttpStatus.OK.value())).build();
    }

    @GetMapping("/{id}")
    public ResponseDTO<HotkeyDTO> get(@PathVariable(value = "id") int id) {
        return ResponseDTO.<HotkeyDTO>builder().code(String.valueOf(HttpStatus.OK.value()))
                .data(hotkeyService.get(id)).build();
    }

    @DeleteMapping("/{id}")
    public ResponseDTO<Void> delete(@PathVariable(value = "id") int id) {
        hotkeyService.delete(id);
        return ResponseDTO.<Void>builder().code(String.valueOf(HttpStatus.OK.value())).build();
    }

    @DeleteMapping("/delete/all/{ids}")
    public ResponseDTO<Void> deleteAll(@PathVariable(value = "ids") List<Integer> ids) {
        hotkeyService.deleteAll(ids);
        return ResponseDTO.<Void>builder().code(String.valueOf(HttpStatus.OK.value())).build();
    }


    @PostMapping("/search")
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseDTO<List<HotkeyDTO>> search(@RequestBody @Valid SearchDTO searchDTO) {
        return hotkeyService.find(searchDTO);
    }


}
