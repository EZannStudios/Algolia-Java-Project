package com.challenge.module.helpers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.lang.reflect.Field;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class JwtHelperTest {

    @InjectMocks
    private JwtHelper jwtHelper;

    @Mock
    private UserDetails userDetails;

    private String secretKey = "verysecretkeywhichisverysecureandlongenough";

    @BeforeEach
    void setUp() throws NoSuchFieldException, IllegalAccessException {
        MockitoAnnotations.openMocks(this);
        jwtHelper = new JwtHelper();
        Field field = JwtHelper.class.getDeclaredField("secretKey");
        field.setAccessible(true);
        field.set(jwtHelper, secretKey);
    }

    @Test
    void generateToken_andValidateToken_returnTrue() {
        String username = "user";
        UserDetails userDetails = User.builder()
                .username(username)
                .password("password")
                .authorities("USER")
                .build();

        String token = jwtHelper.generateToken(userDetails);

        Boolean isValid = jwtHelper.validateToken(token, userDetails);

        assertTrue(isValid);
        assertEquals(username, jwtHelper.extractUsername(token));
    }

    @Test
    void extractExpiration_afterSetting() {
        UserDetails userDetails = User.builder()
                .username("user")
                .password("password")
                .authorities("USER")
                .build();

        String token = jwtHelper.generateToken(userDetails);
        Date expirationDate = jwtHelper.extractExpiration(token);

        assertTrue(expirationDate.after(new Date()));
    }
}
