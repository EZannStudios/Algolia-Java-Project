package com.challenge.module.controllers;

import com.challenge.module.helpers.JwtHelper;
import com.challenge.module.models.JwtRequest;
import com.challenge.module.models.JwtResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class UserAuthenticationController {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtHelper jwtHelper;

    @Autowired
    private AuthenticationManager authenticationManager;

    private Logger logger = LoggerFactory.getLogger(UserAuthenticationController.class);

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody final JwtRequest request) {

        this.doAuthenticate(request.getUsername(), request.getPassword());

        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
        String token = this.jwtHelper.generateToken(userDetails);

        JwtResponse response = JwtResponse.builder()
                .jwtToken(token).username(userDetails.getUsername()).build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    private void doAuthenticate(final String email, final String password) {

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(email, password);
        try {
            authenticationManager.authenticate(authentication);
        } catch (Exception e) {
            throw new BadCredentialsException("Invalid Username or password!");

        }
    }

    @ExceptionHandler(BadCredentialsException.class)
    public String exceptionHandler() {

        return "Invalid Credentials";
    }
}
