package com.springboot.keycloak.controllers;

import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
public class HomeController {
	@GetMapping("/")
	public HashMap<String,String> index() {
        OAuth2User user = (OAuth2User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return new HashMap<String,String>(){{
            put("hello", user.getAttribute("name"));
            put("your email is ", user.getAttribute("email"));
        }};
	}
	
    @GetMapping("/unauthentificated")
    public HashMap<String,String> unauthentificated() {
        return new HashMap<String,String>(){{
            put("this is ", "unauthentificated endpoint");
        }};
    }
}
