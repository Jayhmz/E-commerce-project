package com.plantationhub.wesesta.authentication.jwt;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JwtUserDetails {
    private String firstname;
    private String role;

}
