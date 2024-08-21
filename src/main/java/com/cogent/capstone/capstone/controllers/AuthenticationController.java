package com.cogent.capstone.capstone.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cogent.capstone.capstone.dtos.LoginUserDto;
import com.cogent.capstone.capstone.dtos.RegisterUserDto;
import com.cogent.capstone.capstone.entities.User;
import com.cogent.capstone.capstone.res.LoginResponse;
import com.cogent.capstone.capstone.services.AuthenticationService;
import com.cogent.capstone.capstone.services.JwtService;

@RequestMapping("/auth")
@RestController
@CrossOrigin
public class AuthenticationController {
    private final JwtService jwtService;

    private final AuthenticationService authenticationService;

    public AuthenticationController(JwtService jwtService, AuthenticationService authenticationService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/signup")
    public ResponseEntity<User> register(@RequestBody RegisterUserDto registerUserDto) {
        User registeredUser = authenticationService.signup(registerUserDto);

        return ResponseEntity.ok(registeredUser);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginUserDto loginUserDto) {
        User authenticatedUser = authenticationService.authenticate(loginUserDto);

        String jwtToken = jwtService.generateToken(authenticatedUser);

        LoginResponse loginResponse = new LoginResponse(jwtToken, jwtService.getExpirationTime(),
                authenticatedUser.getAuthorities(), authenticatedUser.getEmail(), authenticatedUser.isActive(),
                authenticatedUser.getId());

        return ResponseEntity.ok(loginResponse);
    }
}