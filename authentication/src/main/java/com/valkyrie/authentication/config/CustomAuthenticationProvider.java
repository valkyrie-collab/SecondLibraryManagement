package com.valkyrie.authentication.config;

import org.bouncycastle.crypto.RuntimeCryptoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {
    private CustomUserDetailsService customUserDetailsService;
    @Autowired
    private void setCustomUserDetailsService(CustomUserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }

    private BCryptPasswordEncoder encoder;
    @Autowired
    private void setEncoder(BCryptPasswordEncoder encoder) {this.encoder = encoder;}

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);

        if(!encoder.matches(password, userDetails.getPassword())) {
            throw new RuntimeCryptoException();
        }

        return new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());
    
    }

    @Override
    public boolean supports(Class<?> authentication) {
        
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);

    }

}
