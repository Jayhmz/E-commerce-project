package com.plantationhub.wesesta.authentication.service.authentication;

import com.plantationhub.wesesta.authentication.dto.SignInDTO;
import com.plantationhub.wesesta.authentication.jwt.JwtUserDetails;
import com.plantationhub.wesesta.authentication.jwt.JwtUtil;
import com.plantationhub.wesesta.authentication.model.AppUser;
import com.plantationhub.wesesta.authentication.service.registration.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class BasicAuthenticationServiceImpl implements AuthenticationService{

    @Value("${secret.key}")
    private String secretKey;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private UserService userService;

    @Override
    public String authenticate(SignInDTO signInDTO) {
        var authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(signInDTO.getUsername(), signInDTO.getPassword())
        );
        if (authenticate.isAuthenticated()){
            var appUser = (AppUser) authenticate.getPrincipal();
            //send login notification email
            return  jwtUtil.generateToken(getJwtDetails(appUser.getFirstName(), getRoles(authenticate)), secretKey);//jwt token
        }else{
            throw new BadCredentialsException("Incorrect username or password");
        }
    }

    private JwtUserDetails getJwtDetails(String name, String role) {
        return JwtUserDetails.builder()
                .firstname(name)
                .role(role)
                .build();
    }

    private String getRoles(Authentication authentication){
        var roles = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
                return roles;
    }

}
