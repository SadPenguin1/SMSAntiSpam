package com.isttmicroservice.smsantispam.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.isttmicroservice.smsantispam.entity.Role;
import com.isttmicroservice.smsantispam.repository.UserRepo;

import java.util.ArrayList;
import java.util.List;

@Service
public class LoginService implements UserDetailsService {
	@Autowired
	UserRepo userRepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	com.isttmicroservice.smsantispam.entity.User  user = userRepo.findByUsername(username).stream().findFirst().orElse(null);
		// nguồn xác thực đọc từ Database
		if(user == null) {
			throw new UsernameNotFoundException("User Not Found");
		}

		List<SimpleGrantedAuthority> list = new ArrayList<SimpleGrantedAuthority>();

		Role role = user.getRole();
			
			 list.add(new SimpleGrantedAuthority(role.getRole()));
			 System.out.println( list.add(new SimpleGrantedAuthority(role.getRole())));
			
		

		// tạo user của Security
		// user đăng nhập hiện tại
		LoginUser currentUser = new LoginUser(user.getId(), username, user.getPassword(), list);
		

		return  (UserDetails) currentUser;
	}
}
