package com.api.watchshop;

import com.api.watchshop.entity.User;
import com.api.watchshop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class WatchshopApplication {

	public static void main(String[] args) {
		SpringApplication.run(WatchshopApplication.class, args);
	}

}
