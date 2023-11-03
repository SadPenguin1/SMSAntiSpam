package com.isttmicroservice.smsantispam.api;

import java.security.Principal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.isttmicroservice.smsantispam.dto.JwtRefreshRequestDTO;
import com.isttmicroservice.smsantispam.dto.JwtResponse;
import com.isttmicroservice.smsantispam.dto.ResponseDTO;
import com.isttmicroservice.smsantispam.dto.UserDTO;
import com.isttmicroservice.smsantispam.entity.LoginRequest;
import com.isttmicroservice.smsantispam.entity.RefreshToken;
import com.isttmicroservice.smsantispam.entity.Role;
import com.isttmicroservice.smsantispam.entity.User;
import com.isttmicroservice.smsantispam.repository.RefreshTokenRepo;
import com.isttmicroservice.smsantispam.repository.RoleRepo;
import com.isttmicroservice.smsantispam.repository.UserRepo;
import com.isttmicroservice.smsantispam.service.JwtTokenService;
import com.isttmicroservice.smsantispam.service.RefreshTokenService;
import com.isttmicroservice.smsantispam.service.UserService;

@RestController
@RequestMapping("/api/auth")
public class AuthAPIController {
	@Autowired
	UserRepo userRepository;
	@Autowired
	RoleRepo roleRepository;
	@Autowired
	AuthenticationManager authenticationManager;
	@Autowired
	PasswordEncoder encoder;
	@Autowired
	JwtTokenService jwtTokenService;
	@Autowired
	RefreshTokenService refreshTokenService;
	@Autowired
	UserService userService;

	@Autowired
	RefreshTokenRepo refreshTokenRepo;

	@GetMapping("/me")
//	@PreAuthorize("isAuthenticated()")
	public ResponseDTO<UserDTO> me(Principal principal) {
		System.out.println(principal);
		String username = principal.getName();

		return ResponseDTO.<UserDTO>builder().code(String.valueOf(HttpStatus.OK.value()))
				.data(userService.getUserByUsername(username)).build();
	}

	@GetMapping("/current")
	public Principal getUser(Principal principal) {
		return principal;
	}

//    @PostMapping("/signin")
//    public ResponseDTO<String> login(@Valid @RequestBody LoginRequest loginRequest) {
// 
//        Authentication authentication= authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
//        return ResponseDTO.<String>builder().code(String.valueOf(HttpStatus.OK.value())).data(jwtTokenService.createToken(loginRequest.getUsername(),authentication.getAuthorities())).build();
//    }
	@PostMapping("/signin")
	public ResponseDTO<JwtResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
		Optional<User> user = userRepository.findByUsername(loginRequest.getUsername());
		// Tạo AccessToken và RefreshToken
		String accessToken = jwtTokenService.createToken(loginRequest.getUsername(), authentication.getAuthorities());
		refreshTokenService.createRefreshToken(user.get().getId());
		RefreshToken refreshToken = refreshTokenRepo.getRefreshTokenById(user.get().getId());
		JwtResponse jwtResponse = new JwtResponse();
		jwtResponse.setToken(accessToken);
		jwtResponse.setRefreshToken(refreshToken.getToken());
		return ResponseDTO.<JwtResponse>builder().code(String.valueOf(HttpStatus.OK.value())).data(jwtResponse).build();

	}

//    @PostMapping("/signup")
//    public ResponseEntity<?>  register(@Valid @RequestBody User userSignUp) {
//        User user = new User();
//        user.setEmail(userSignUp.getEmail());
//        user.setUsername(userSignUp.getUsername());
//        user.setPassword(encoder.encode(userSignUp.getPassword()));
//        System.out.println(encoder.encode(userSignUp.getPassword()));
//        List<Role>strRoles = new ArrayList<Role>();
//        strRoles.add(roleRepository.findById(1).orElseThrow(null));
//
//        user.setRole(strRoles);
//        userRepository.save(user);
//
//        return ResponseEntity.ok("User registered successfully!");
//    }

	@PostMapping("/refresh")
	public ResponseDTO<JwtResponse> refreshToken(@RequestBody JwtRefreshRequestDTO jwtRefreshRequestDTO) {
		Instant now = Instant.now();
		System.out.println("1235");
		System.out.println(jwtRefreshRequestDTO.getRefreshToken());
		Optional<RefreshToken> refreshToken = refreshTokenRepo.findByToken(jwtRefreshRequestDTO.getRefreshToken());
		if (refreshToken.isEmpty()) {
			throw new RuntimeException("Refresh Token not found!"+ jwtRefreshRequestDTO.getRefreshToken());

		}
		RefreshToken refreshToken2 = refreshToken.get();
		if (now.isAfter(refreshToken2.getExpiryDate())) {
			refreshTokenRepo.delete(refreshToken2);
			throw new RuntimeException("Refresh Token was expired!"+ jwtRefreshRequestDTO.getRefreshToken());
		} else {
			Optional<User> user = userRepository.findByUsername(refreshToken2.getUser().getUsername());
			Role role = refreshToken2.getUser().getRole();
			List<GrantedAuthority> authorities = new ArrayList<>();
			authorities.add(new SimpleGrantedAuthority(role.getRole()));
//			  Collection<? extends GrantedAuthority> authorities = refreshToken2.getUser().getRole();
//			    String accessToken = jwtTokenService.createToken(username, authorities);
//			Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
//					user.get().getUsername(), jwtRefreshRequestDTO.getPassword()));

			// Tạo AccessToken và RefreshToken
			String accessToken = jwtTokenService.createToken(user.get().getUsername(), authorities);

			refreshTokenService.createRefreshToken(user.get().getId());
			JwtResponse jwtResponse = new JwtResponse();
			RefreshToken refreshToken3 = refreshTokenRepo.getRefreshTokenById(user.get().getId());
			jwtResponse.setUsername(user.get().getUsername());
			jwtResponse.setToken(accessToken);
			jwtResponse.setRefreshToken(refreshToken3.getToken());
			return ResponseDTO.<JwtResponse>builder().code(String.valueOf(HttpStatus.OK.value())).data(jwtResponse)
					.build();

		}

	}
}
