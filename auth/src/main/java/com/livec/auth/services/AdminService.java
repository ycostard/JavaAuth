package com.livec.auth.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.livec.auth.DTOS.UserDTO;
import com.livec.auth.Repositories.UserRepository;
import com.livec.auth.configurations.JWTTokenProvider;
import com.livec.auth.enums.Role;
import com.livec.auth.models.User;
//import com.livec.auth.services.security.CustomPasswordEncoder;

@Service
public class AdminService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    
    //@Autowired
    //private CustomPasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JWTTokenProvider jwtTokenProvider;

    public String signup(UserDTO userDTO) {

        User user = new User(userDTO.getUsername(), userDTO.getPassword());
        
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        List<Role> roles = new ArrayList<>();
        roles.add(Role.ROLE_ADMIN);
        user.setRole(roles);
        userRepository.save(user);

        //Role is collection empty
        return jwtTokenProvider.createToken(user);
    }

    public String signin(String username, String password) throws BadCredentialsException {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            if(!userRepository.findByUsername(username).get().getRole().contains(Role.ROLE_ADMIN)) {
                throw new BadCredentialsException("No admin");
            }
            return jwtTokenProvider.createToken(userRepository.findByUsername(username).get());
        } catch (Exception e) {
            throw e;
        }
    }
}
