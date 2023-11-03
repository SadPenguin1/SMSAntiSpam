package com.isttmicroservice.smsantispam;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.isttmicroservice.smsantispam.entity.User;
import com.isttmicroservice.smsantispam.repository.UserRepo;

import java.util.Optional;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
public class JPAConfiguration {
    @Autowired
	UserRepo userRepository;

	@Bean
	AuditorAware<User> auditorProvider() {
		return new AuditorAware<User>() {
			@Override
			public Optional<User> getCurrentAuditor() {
				Authentication auth = SecurityContextHolder.getContext().getAuthentication();
				System.out.println(auth+" "+auth.getName()+" "+auth.getAuthorities()+" "+ auth.getDetails());
				User user =null;
				if (auth != null && !(auth instanceof AnonymousAuthenticationToken))
					user = userRepository.findByUsername(auth.getName()).orElse(null);
				return Optional.ofNullable(user);

			}
		};
	}
}
