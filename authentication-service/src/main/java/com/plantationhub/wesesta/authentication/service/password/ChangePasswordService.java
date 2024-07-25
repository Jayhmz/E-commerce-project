package com.plantationhub.wesesta.authentication.service.password;

import com.plantationhub.wesesta.authentication.dto.ChangeOldPasswordDTO;
import com.plantationhub.wesesta.authentication.service.registration.UserService;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ChangePasswordService {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public ChangePasswordService(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    public void changePassword(ChangeOldPasswordDTO changePasswordDTO){
        var authentication = SecurityContextHolder.getContext().getAuthentication();

        var user = userService.findByMail(authentication.getName())
                .orElseThrow(()->new BadCredentialsException("Incorrect user details"));

        if (changePasswordDTO.getPassword().equals(changePasswordDTO.getConfirmPassword())){
            user.setPassword(passwordEncoder.encode(changePasswordDTO.getPassword()));
            userService.saveUser(user);
        }
    }
}
