package com.livec.auth.services.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.livec.auth.Repositories.UserRepository;
import com.livec.auth.models.User;

@Service
public class UserAuthService implements UserDetailsService{
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws BadCredentialsException {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new BadCredentialsException("Log/Pass incorrect"));
        return new User(user.getId(), user.getUsername(), user.getPassword(), user.getRole(), user.isAccountNonLocked());
    }
}
