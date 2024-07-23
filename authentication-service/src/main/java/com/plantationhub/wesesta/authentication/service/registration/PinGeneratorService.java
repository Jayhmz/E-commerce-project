package com.plantationhub.wesesta.authentication.service.registration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class PinGeneratorService {

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public PinGeneratorService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public String generatePin(){
        Random random = new Random();
        int pin = 100000 + random.nextInt(900000);
        return passwordEncoder.encode(String.valueOf(pin));
    }
}
