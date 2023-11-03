package com.isttmicroservice.smsantispam.api;

import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.isttmicroservice.smsantispam.dto.EmailDTO;
import com.isttmicroservice.smsantispam.dto.ResponseDTO;
import com.isttmicroservice.smsantispam.dto.SearchDTO;
import com.isttmicroservice.smsantispam.dto.UserDTO;

@RestController
@RequestMapping("/user")
public class UserAPIController {
	@Autowired
	private com.isttmicroservice.smsantispam.service.UserService userService;

	@PostMapping("/")
//	 @PreAuthorize("hasRole('ROLE_ADMIN')")
// 11111111
	public ResponseDTO<UserDTO> create(@ModelAttribute @Valid UserDTO userDTO) throws IOException {

		userService.create(userDTO);
		return ResponseDTO.<UserDTO>builder().code(String.valueOf(HttpStatus.OK.value())).data(userDTO).build();
	}

	@GetMapping("/me")
//	@PreAuthorize("isAuthenticated()")
	public ResponseDTO<UserDTO> me(Principal principal) {
		String username = principal.getName();

		return ResponseDTO.<UserDTO>builder().code(String.valueOf(HttpStatus.OK.value()))
				.data(userService.getUserByUsername(username)).build();
	}

	@PutMapping("/")
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseDTO<UserDTO> update(@ModelAttribute @Valid UserDTO userDTO) throws IOException {
		userService.update(userDTO);
//		return ResponseDTO.<Void>builder().code(String.valueOf(HttpStatus.OK.value())).build();
		return ResponseDTO.<UserDTO>builder().code(String.valueOf(HttpStatus.OK.value())).data(userDTO).build();

	}

	@GetMapping("/{id}")
	// @PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseDTO<UserDTO> get(@PathVariable(value = "id") int id) {
		return ResponseDTO.<UserDTO>builder().code(String.valueOf(HttpStatus.OK.value())).data(userService.get(id))
				.build();
	}

	@DeleteMapping("/{id}")
	 @PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseDTO<Void> delete(@PathVariable(value = "id") int id) {

		userService.delete(id);

		return ResponseDTO.<Void>builder().code(String.valueOf(HttpStatus.OK.value())).build();
	}

	@DeleteMapping("/delete/all/{ids}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseDTO<Void> deleteAll(@PathVariable(value = "ids") List<Integer> ids) {
		userService.deleteAll(ids);
		return ResponseDTO.<Void>builder().code(String.valueOf(HttpStatus.OK.value())).build();
	}

	@PostMapping("/search")
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_MEMBER')")
	public ResponseDTO<List<UserDTO>> search(@RequestBody @Valid SearchDTO searchDTO) {
		return userService.find(searchDTO);
	}

	@PutMapping("/update-password")
	public ResponseDTO<Void> updatePassword(@ModelAttribute @Valid UserDTO userDTO) throws IOException {
		userService.updatePassword(userDTO);
		return ResponseDTO.<Void>builder().code(String.valueOf(HttpStatus.OK.value())).build();
	}

	@PutMapping("/forgot-password")
	public ResponseDTO<Void> forgetPassword(@ModelAttribute @Valid UserDTO userDTO, EmailDTO emailDTO)
			throws IOException {
		userDTO.setPassword(renderRandomString(10));
		System.out.println(userDTO.getPassword());
		userService.forgetPassword(userDTO, emailDTO);

		return ResponseDTO.<Void>builder().code(String.valueOf(HttpStatus.OK.value())).build();
	}

	public String renderRandomString(int length) {

		String validChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
		StringBuilder randomString = new StringBuilder();
		Random random = new Random();
		for (int i = 0; i < length; i++) {
			int randomIndex = random.nextInt(validChars.length());
			char randomChar = validChars.charAt(randomIndex);
			randomString.append(randomChar);
		}
		return randomString.toString();
	}

}
