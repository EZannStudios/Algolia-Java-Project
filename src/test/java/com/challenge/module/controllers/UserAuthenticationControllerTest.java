package com.challenge.module.controllers;

import com.challenge.module.helpers.JwtHelper;
import com.challenge.module.models.JwtRequest;
import com.challenge.module.models.JwtResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

public class UserAuthenticationControllerTest {

    @Mock
    private UserDetailsService userDetailsService;

    @Mock
    private JwtHelper jwtHelper;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private UserAuthenticationController userAuthenticationController;

    @BeforeEach
    void setUp() {
        openMocks(this);
    }

    @Test
    void loginSuccessful() {
        JwtRequest jwtRequest = new JwtRequest("user", "password");
        UserDetails mockUserDetails = mock(UserDetails.class);
        when(userDetailsService.loadUserByUsername("user")).thenReturn(mockUserDetails);
        when(mockUserDetails.getUsername()).thenReturn("user");
        when(jwtHelper.generateToken(mockUserDetails)).thenReturn("mockToken");

        ResponseEntity<JwtResponse> response = userAuthenticationController.login(jwtRequest);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("mockToken", response.getBody().getJwtToken());
        assertEquals("user", response.getBody().getUsername());
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
    }

    @Test
    void loginFailureBadCredentials() {
        JwtRequest jwtRequest = new JwtRequest("user", "wrongpassword");
        doThrow(new BadCredentialsException("Invalid Username or password!"))
                .when(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));

        Exception exception = assertThrows(BadCredentialsException.class, () -> {
            userAuthenticationController.login(jwtRequest);
        });

        assertEquals("Invalid Username or password!", exception.getMessage());
    }
}
