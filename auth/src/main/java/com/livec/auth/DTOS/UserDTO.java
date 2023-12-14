package com.livec.auth.DTOS;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.livec.auth.exceptions.ValidPassword;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {
    
    @JsonProperty("username")
    private String username;

    @ValidPassword
    private String password;
}
