package com.abdelrahman.shoppingcart.configurations;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.abdelrahman.shoppingcart.enums.Role;
import com.abdelrahman.shoppingcart.models.User;
import com.abdelrahman.shoppingcart.repositories.UserRepo;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Configuration
@RequiredArgsConstructor
@Log4j2
@Profile("prod")
public class StartUpConfiguration implements CommandLineRunner {

	private final UserRepo userRepo;
	private final PasswordEncoder encoder;
	
	@Value("${ADMIN_EMAIL}")
	private String email;
	@Value("${ADMIN_PASSWORD}")
	private String password;
	@Override
	public void run(String... args) throws Exception {
		if(!userRepo.existsByEmail("admin@gmail.com")) {
			User user = new User();
			user.setEmail(email);
			user.setPassword(encoder.encode(password));
			user.setRole(Role.ROLE_ADMIN);
			user.setUsername("Admin");
			userRepo.save(user);
			log.warn("Admin created");
		}
		
	}

}
