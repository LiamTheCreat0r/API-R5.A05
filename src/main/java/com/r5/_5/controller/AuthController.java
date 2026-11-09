package com.r5._5.controller;

import java.util.List;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import com.r5._5.security.JwtDTO;
import com.r5._5.security.LoginRequest;
import com.r5._5.security.TokenGenerator;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final TokenGenerator tokenGenerator;

    public AuthController(AuthenticationManager authenticationManager, TokenGenerator tokenGenerator) {
        this.authenticationManager = authenticationManager;
        this.tokenGenerator = tokenGenerator;
    }

    @PostMapping("/login")
    public JwtDTO login(@Valid @RequestBody LoginRequest request) {
        // Lève BadCredentialsException (-> 401) si login/mot de passe invalides
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.username(), request.password()));

        String token = tokenGenerator.generateJwtToken(authentication);
        UserDetails user = (UserDetails) authentication.getPrincipal();
        List<String> roles = user.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        return new JwtDTO(token, user.getUsername(), roles);
    }
}
