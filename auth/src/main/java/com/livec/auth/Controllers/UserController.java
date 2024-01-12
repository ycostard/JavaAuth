package com.livec.auth.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RestController;

import com.livec.auth.DTOS.UserDTO;
import com.livec.auth.models.User;
import com.livec.auth.services.UserService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;




@RestController
public class UserController {

    @Autowired  
    private UserService userService;


    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    @PostMapping("/signup")
    public ResponseEntity<String> createUser(@RequestBody @Valid UserDTO userDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(bindingResult.getFieldError().getDefaultMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(userService.signup(userDTO), HttpStatus.CREATED);
        
    }

    @PostMapping("/signin")
    public ResponseEntity<String> connectUser(@RequestBody UserDTO userDTO) {
        try {
            String token = userService.signin(userDTO.getUsername(), userDTO.getPassword());
            return new ResponseEntity<>(token, HttpStatus.OK);
        } catch (BadCredentialsException | LockedException e) {
            return new ResponseEntity<>(e.getMessage().toString(), HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/users/locked")
    public ResponseEntity<List<User>> getAllUsersLocked() {
        return new ResponseEntity<>(userService.getAllUsersLocked(), HttpStatus.OK);
    }
    
}
