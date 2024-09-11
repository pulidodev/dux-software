package com.duxsoftware.challenge.service.impl;

import java.util.Collections;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.duxsoftware.challenge.config.jwt.JwtUtils;
import com.duxsoftware.challenge.dto.AuthRequestDTO;
import com.duxsoftware.challenge.dto.AuthResponseDTO;
import com.duxsoftware.challenge.model.UserSec;
import com.duxsoftware.challenge.repository.IUserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final IUserRepository repository;
    private final JwtUtils jwtUtils;
    private final PasswordEncoder passwordEncoder;

    private static final String BAD_CREDENTIALS_MESSAGE = "Invalid username or password";

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserSec userSec = repository.findUserEntityByUsername(username)
                .orElseThrow(() -> new BadCredentialsException(BAD_CREDENTIALS_MESSAGE));
        return new User(userSec.getUsername(),
                userSec.getPassword(),
                userSec.isEnabled(),
                userSec.isAccountNotExpired(),
                userSec.isCredentialNotExpired(),
                userSec.isAccountNotLocked(),
                Collections.emptyList());
    }

    public AuthResponseDTO login(AuthRequestDTO request) {

        String username = request.username();
        String password = request.password();

        Authentication authentication = this.authenticate(username, password);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String accessToken = jwtUtils.createToken(authentication);
        return new AuthResponseDTO(accessToken);
    }

    public Authentication authenticate(String username, String password) {
        UserDetails userDetails = this.loadUserByUsername(username);
        if (userDetails == null) {
            throw new BadCredentialsException(BAD_CREDENTIALS_MESSAGE);
        }
        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException(BAD_CREDENTIALS_MESSAGE);
        }
        return new UsernamePasswordAuthenticationToken(username, userDetails.getPassword(),
                userDetails.getAuthorities());
    }
}
