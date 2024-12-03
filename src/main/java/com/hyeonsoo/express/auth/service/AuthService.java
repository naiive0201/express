package com.hyeonsoo.express.auth.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AuthService implements UserDetailsService {

    @Value("${express.name}")
    private String expressName;

    @Value("${express.password}")
    private String expressPassword;

    @Value("${express.roles}")
    private String expressRoles;

    private final PasswordEncoder passwordEncoder;


    public AuthService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (!expressName.equals(username)) {
            throw new UsernameNotFoundException("User not found");
        }

        return User.builder()
                .username(username)
                .password(expressPassword)
                .passwordEncoder(passwordEncoder::encode)
                .roles(expressRoles)
                .build();
    }
}
