package com.plantationhub.email.controller;

import com.plantationhub.email.service.SendMail;
import jakarta.mail.MessagingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/mail")
@Slf4j
public class RegistrationMailController {

    private final SendMail sendMail;

    public RegistrationMailController(@Qualifier("sendRegistrationSuccessfulMail") SendMail sendMail) {
        this.sendMail = sendMail;
    }

    @PostMapping("/send-onboarding-token-mail")
    public ResponseEntity<?> sendRegistrationTokenMail(@RequestParam String email, @RequestParam int token) throws MessagingException {
        sendMail.sendMail(email, String.valueOf(token));
        return new ResponseEntity<>("Email has been sent successfully",HttpStatus.OK);
    }

}
