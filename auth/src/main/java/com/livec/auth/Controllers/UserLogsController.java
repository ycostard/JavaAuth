package com.livec.auth.Controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.livec.auth.models.UserLogs;
import com.livec.auth.services.UserLogsService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/user-logs")
public class UserLogsController {
    
    @Autowired
    private UserLogsService userLogsService;

    @GetMapping("")
    public ResponseEntity<List<UserLogs>> getAllLogs() {
        return new ResponseEntity<>(userLogsService.getAllLogs(), HttpStatus.OK);
    }
    
}
