package com.plantationhub.email.controller;

import com.plantationhub.email.service.SendMail;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/mail")
public class AuthenticationMailController {

    private final SendMail sendMail;

    public AuthenticationMailController(@Qualifier("sendRegistrationSuccessfulMail") SendMail sendMail) {
        this.sendMail = sendMail;
    }

    @GetMapping("/send-login-notification")
    public ResponseEntity<?> sendTokenMail(String email, String username) throws MessagingException {
        sendMail.sendMail(email, username);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/send-registration-mail")
    public ResponseEntity<?> sendRegistrationMail(String email, String username) throws MessagingException {
        sendMail.sendMail(email, username);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
