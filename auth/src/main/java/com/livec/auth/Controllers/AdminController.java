package com.livec.auth.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.livec.auth.DTOS.UserDTO;
import com.livec.auth.services.AdminService;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired  
    private AdminService adminService;

    @PostMapping("/signup")
    public ResponseEntity<String> createAdmin(@RequestBody @Valid UserDTO userDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(bindingResult.getFieldError().getDefaultMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(adminService.signup(userDTO), HttpStatus.CREATED);
        
    }

    @PostMapping("/signin")
    public ResponseEntity<String> connectAdmin(@RequestBody UserDTO userDTO) {
        try {
            String token = adminService.signin(userDTO.getUsername(), userDTO.getPassword());
            return new ResponseEntity<>(token, HttpStatus.OK);
        } catch (BadCredentialsException e) {
            return new ResponseEntity<>(e.getMessage().toString(), HttpStatus.UNAUTHORIZED);
        }
    }
}
