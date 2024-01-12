package com.livec.auth.Controllers;

import java.security.Principal;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {
    
    @GetMapping("/admin")
    public ResponseEntity<String> getAdmin(Principal principal) {
        return new ResponseEntity<>("Hello Admin \nUser Name : " + principal.getName(), HttpStatus.OK);
    }

    @GetMapping("/user")
    public ResponseEntity<String> getUser(Principal principal) {
        return new ResponseEntity<>("Hello User \nUser Name : " + principal.getName(), HttpStatus.OK);
    }

    @GetMapping("/anonymous")
    public ResponseEntity<String> getAnonymous() {
        return new ResponseEntity<>("Hello Anonymous", HttpStatus.OK);
    }
}
