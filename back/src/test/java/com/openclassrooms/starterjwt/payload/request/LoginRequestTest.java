package com.openclassrooms.starterjwt.payload.request;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LoginRequestTest {

    @Test
    void testLoginRequestGettersAndSetters() {
        // Arrange
        LoginRequest loginRequest = new LoginRequest();

        // Act
        loginRequest.setEmail("test@test.com");
        loginRequest.setPassword("password123");

        // Assert
        assertEquals("test@test.com", loginRequest.getEmail());
        assertEquals("password123", loginRequest.getPassword());
    }

    @Test
    void testLoginRequestEmptyConstructor() {
        // Arrange & Act
        LoginRequest loginRequest = new LoginRequest();

        // Assert
        assertNull(loginRequest.getEmail());
        assertNull(loginRequest.getPassword());
    }
}