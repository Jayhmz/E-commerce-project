package com.plantationhub.wesesta.authentication.service.registration;

import com.plantationhub.wesesta.authentication.model.AppUser;
import com.plantationhub.wesesta.authentication.repository.AppUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final AppUserRepository userRepository;

    public void saveUser(AppUser user){
        userRepository.save(user);
    }
    public Optional<AppUser> findByPhoneOrEmail(String phoneOrEmail){
        return userRepository.findByPhoneOrEmail(phoneOrEmail);
    }
    public Optional<AppUser> findByMail(String email){
        return userRepository.findByEmail(email);
    }
    public void deactivateUser(String email){
        userRepository.deactivateUser(email);
    }
}
