package com.coursemaster.authservice;

import com.coursemaster.authservice.auth.api.RegisterRequest;
import com.coursemaster.authservice.auth.domain.AuthenticationService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import static com.coursemaster.authservice.user.api.Role.*;

@SpringBootApplication
public class AuthServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuthServiceApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(AuthenticationService authService) {
		return args -> {
			var admin = RegisterRequest.builder()
					.firstname("Admin")
					.lastname("Admin")
					.email("admin@mail.com")
					.password("password")
					.role(ADMIN)
					.build();
			authService.register(admin);
			var instructor = RegisterRequest.builder()
					.firstname("instructor")
					.lastname("instructor")
					.email("lect@o2.pl")
					.password("instructor")
					.role(INSTRUCTOR)
					.build();
			authService.register(instructor);
			var user = RegisterRequest.builder()
					.firstname("user")
					.lastname("user")
					.email("user@o2.pl")
					.password("useruser")
					.role(USER)
					.build();
			authService.register(user);
		};
	}
}
