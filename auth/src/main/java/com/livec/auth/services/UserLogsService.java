package com.livec.auth.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.livec.auth.Repositories.UserLogsRepository;
import com.livec.auth.models.UserLogs;

@Service
public class UserLogsService {
    
    @Autowired
    private UserLogsRepository userLogsRepository;

    public List<UserLogs> getAllLogs() {
        return userLogsRepository.findAll();
    }

    public void save(UserLogs userLogs) {
        userLogsRepository.save(userLogs);
    }
}
