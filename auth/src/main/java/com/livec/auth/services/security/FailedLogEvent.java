package com.livec.auth.services.security;

import org.springframework.context.ApplicationEvent;

import com.livec.auth.models.User;

public class FailedLogEvent extends ApplicationEvent {
	private static final long serialVersionUID = 33969741564703240L;
	
    private User user;

	public FailedLogEvent(Object source, User user) {
		super(source);
		this.user = user;
	}
	
	public User getUser() {
		return user;
	}    
}
