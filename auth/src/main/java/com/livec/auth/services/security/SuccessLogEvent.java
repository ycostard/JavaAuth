package com.livec.auth.services.security;

import org.springframework.context.ApplicationEvent;

import com.livec.auth.models.User;

public class SuccessLogEvent extends ApplicationEvent {
	private static final long serialVersionUID = 3396974156470324088L;
	
    private User user;

	public SuccessLogEvent(Object source, User user) {
		super(source);
		this.user = user;
	}
	
	public User getUser() {
		return user;
	}    
}
