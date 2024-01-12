package com.livec.auth.services.security;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.livec.auth.models.User;
import com.livec.auth.models.UserLogs;
import com.livec.auth.services.UserLogsService;
import com.livec.auth.services.UserService;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class LoginAttemptService {

    public static final int MAX_ATTEMPT = 4;
    private LoadingCache<String, Integer> attemptsCache;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private UserLogsService userLogsService;

    @Autowired
    private UserService userService;
    
    public LoginAttemptService() {
        super();
        attemptsCache = CacheBuilder.newBuilder().expireAfterWrite(1, TimeUnit.DAYS).build(new CacheLoader<String, Integer>() {
            @Override
            public Integer load(final String key) {
                return 0;
            }
        });
    }

    public void loginFailed(final String key, User user) {
        int attempts;
        try {
            attempts = attemptsCache.get(key);
        } catch (final ExecutionException e) {
            attempts = 0;
        }
        attempts++;
        attemptsCache.put(key, attempts); 
        if(attempts >= MAX_ATTEMPT) {
            userService.updateLockedUser(user, false);
        }
        userLogsService.save(new UserLogs(user.getId(), getClientIP(), "Failed"));
    }

    public void loginSuccess(final String key, User user){
        userLogsService.save(new UserLogs(user.getId(), getClientIP(), "Success"));
    }

    public boolean isBlocked() {
        try {
            return attemptsCache.get(getClientIP()) >= MAX_ATTEMPT;
        } catch (final ExecutionException e) {
            return false;
        }
    }

    private String getClientIP() {
        final String xfHeader = request.getHeader("X-Forwarded-For");
        if (xfHeader != null) {
            return xfHeader.split(",")[0];
        }
        return request.getRemoteAddr();
    }
}