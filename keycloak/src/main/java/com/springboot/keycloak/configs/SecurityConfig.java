package com.springboot.keycloak.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {


	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf(csrf -> csrf.disable());
		http.cors(cors -> cors.disable());
		
		http.oauth2Login((oauth2Login) -> oauth2Login
				.tokenEndpoint()
				.and()
				.userInfoEndpoint()
		);
		
		http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.ALWAYS));
		
		http.authorizeHttpRequests((requests) ->
		requests.requestMatchers("/unauthentificated").permitAll() 
						.requestMatchers("/oauth2/**").permitAll()
						.requestMatchers("/login/**").permitAll()
						.anyRequest().fullyAuthenticated());

        http.logout(logout -> logout
                .logoutSuccessUrl("http://localhost:8090/realms/JavaAuth/protocol/openid-connect"
                        + "logout?redirect_uri=http://localhost:8080/"));
		return http.build();
	}
	
	
}