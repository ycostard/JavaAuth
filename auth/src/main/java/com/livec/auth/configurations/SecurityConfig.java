package com.livec.auth.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.livec.auth.services.security.AppAuthProvider;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	 @Bean
	 public PasswordEncoder passwordEncoder() {
	 return new BCryptPasswordEncoder(10);
	 //return new SCryptPasswordEncoder(2, 8, 1, 10, 16);
	 }

	@Bean
	public AuthenticationManager authenticationManager() {
		return new AppAuthProvider();
	}

	@Bean
	public JWTTokenProvider jwtTokenProvider() {
		return new JWTTokenProvider();
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
				.authorizeHttpRequests((requests) -> requests
						.requestMatchers("/test/anonymous").permitAll()
						.requestMatchers("/signup").permitAll()
						.requestMatchers("/signin").permitAll()
						.requestMatchers("/admin/signup").permitAll()
						.requestMatchers("/admin/signin").permitAll()
						.requestMatchers("/test/user").authenticated()
						.requestMatchers("/test/admin").hasAuthority("ROLE_ADMIN")
						.anyRequest().authenticated())
				.addFilterBefore(new JWTTokenFilter(jwtTokenProvider()), UsernamePasswordAuthenticationFilter.class)
				.sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		
		http.csrf((csrf) -> csrf.disable());
		http.cors((cors) -> cors.disable());

		return http.build();
	}
}