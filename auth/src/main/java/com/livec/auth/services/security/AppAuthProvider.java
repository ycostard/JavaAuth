package com.livec.auth.services.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.livec.auth.models.User;

public class AppAuthProvider implements AuthenticationManager {

    @Autowired
    private UserAuthService userAuthService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;


    @Override
    public Authentication authenticate(Authentication authentication)  {
        UsernamePasswordAuthenticationToken auth = (UsernamePasswordAuthenticationToken) authentication;
        String name = auth.getName();
        String password = auth.getCredentials().toString();

        User user = (User) userAuthService.loadUserByUsername(name);

        if(user.getId() == null){
            throw new BadCredentialsException("Log/Pass incorrect");
        }

        if(!user.isAccountNonLocked()){
            throw new LockedException("Account is locked");
        }
        
        if(!passwordEncoder.matches(password, user.getPassword())){
            // Failed
            if(user != null) {
                applicationEventPublisher.publishEvent(new FailedLogEvent(this, user));
            }
            throw new BadCredentialsException("Log/Pass incorrect");
        }
        //Success
        applicationEventPublisher.publishEvent(new SuccessLogEvent(this, user));

        return new UsernamePasswordAuthenticationToken(name, password, user.getAuthorities());
    }
    
}
