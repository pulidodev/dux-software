package com.duxsoftware.challenge.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.duxsoftware.challenge.dto.AuthRequestDTO;
import com.duxsoftware.challenge.dto.AuthResponseDTO;
import com.duxsoftware.challenge.service.impl.UserDetailsServiceImpl;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@Validated
@RequiredArgsConstructor
@Tag(name = "Auth", description = "Operations related to authentication.")
public class AuthController {

    private final UserDetailsServiceImpl service;

    @Operation(summary = "Auth", description = "Auth Request.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully logged in"),
            @ApiResponse(responseCode = "401", description = "Invalid username or password")
    })
    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody @Valid AuthRequestDTO request) {
        return ResponseEntity.ok(service.login(request));
    }
}
