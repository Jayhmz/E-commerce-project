package com.plantationhub.wesesta.authentication.controller;

import com.plantationhub.wesesta.authentication.dto.SignInDTO;
import com.plantationhub.wesesta.authentication.service.authentication.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @Autowired
    public AuthenticationController(@Qualifier("basicAuthenticationServiceImpl") AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody SignInDTO signInDTO){
        return new ResponseEntity<>(authenticationService.authenticate(signInDTO), HttpStatus.OK);
    }
}
