package com.plantationhub.wesesta.authentication.controller;

import com.plantationhub.wesesta.authentication.dto.ValidateMailDTO;
import com.plantationhub.wesesta.authentication.dto.ValidatePhoneDTO;
import com.plantationhub.wesesta.authentication.service.token.ValidateEmailToken;
import com.plantationhub.wesesta.authentication.service.token.ValidatePhoneToken;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class VerificationController {
    private final ValidatePhoneToken validatePhoneToken;
    private final ValidateEmailToken validateEmailToken;

    @Autowired
    public VerificationController(ValidatePhoneToken validatePhoneToken, ValidateEmailToken validateEmailToken) {
        this.validatePhoneToken = validatePhoneToken;
        this.validateEmailToken = validateEmailToken;
    }

    @PostMapping("/verify-phone")
    public ResponseEntity<?> verifyPhoneNumber(@Valid @RequestBody ValidatePhoneDTO validatePhoneDTO){
        return new ResponseEntity<String>(validatePhoneToken.validateToken(validatePhoneDTO.getToken(), validatePhoneDTO.getPhone()), HttpStatus.OK);
    }

    @PostMapping("/verify-email")
    public ResponseEntity<?> verifyMail(@Valid @RequestBody ValidateMailDTO validateMailDTO){
        return new ResponseEntity<String>(validateEmailToken.validateToken(validateMailDTO.getToken(), validateMailDTO.getEmail()), HttpStatus.OK);
    }

}
