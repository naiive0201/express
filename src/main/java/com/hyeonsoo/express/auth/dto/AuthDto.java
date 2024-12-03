package com.hyeonsoo.express.auth.dto;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AuthDto {
    private String username;
    private String password;
}
