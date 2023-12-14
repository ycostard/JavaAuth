package com.livec.auth.services.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.livec.auth.models.User;

public class AppAuthProvider implements AuthenticationManager {

    @Autowired
    private UserAuthService userAuthService;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        UsernamePasswordAuthenticationToken auth = (UsernamePasswordAuthenticationToken) authentication;
        String name = auth.getName();
        String password = auth.getCredentials().toString();

        User user = (User) userAuthService.loadUserByUsername(name);
        //check password with password user
        if(user == null || !passwordEncoder.matches(password, user.getPassword())){
            throw new BadCredentialsException("Log/Pass incorrect");
        }
        return new UsernamePasswordAuthenticationToken(name, password, user.getAuthorities());
    }
    
}
