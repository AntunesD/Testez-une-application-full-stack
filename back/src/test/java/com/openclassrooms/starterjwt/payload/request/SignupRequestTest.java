package com.openclassrooms.starterjwt.payload.request;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SignupRequestTest {

    @Test
    void testSignupRequestGettersAndSetters() {
        // Arrange
        SignupRequest signupRequest = new SignupRequest();

        // Act
        signupRequest.setEmail("test@test.com");
        signupRequest.setFirstName("John");
        signupRequest.setLastName("Doe");
        signupRequest.setPassword("password123");

        // Assert
        assertEquals("test@test.com", signupRequest.getEmail());
        assertEquals("John", signupRequest.getFirstName());
        assertEquals("Doe", signupRequest.getLastName());
        assertEquals("password123", signupRequest.getPassword());
    }

    @Test
    void testSignupRequestEmptyConstructor() {
        // Arrange & Act
        SignupRequest signupRequest = new SignupRequest();

        // Assert
        assertNull(signupRequest.getEmail());
        assertNull(signupRequest.getFirstName());
        assertNull(signupRequest.getLastName());
        assertNull(signupRequest.getPassword());
    }

    @Test
    void testSignupRequestEqualsAndHashCode() {
        // Arrange
        SignupRequest request1 = new SignupRequest();
        request1.setEmail("test@test.com");
        request1.setFirstName("John");
        request1.setLastName("Doe");
        request1.setPassword("password123");

        SignupRequest request2 = new SignupRequest();
        request2.setEmail("test@test.com");
        request2.setFirstName("John");
        request2.setLastName("Doe");
        request2.setPassword("password123");

        SignupRequest request3 = new SignupRequest();
        request3.setEmail("other@test.com");
        request3.setFirstName("Jane");
        request3.setLastName("Doe");
        request3.setPassword("password123");

        // Assert
        assertEquals(request1, request2);
        assertNotEquals(request1, request3);
        assertEquals(request1.hashCode(), request2.hashCode());
        assertNotEquals(request1.hashCode(), request3.hashCode());
    }

    @Test
    void testSignupRequestToString() {
        // Arrange
        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setEmail("test@test.com");
        signupRequest.setFirstName("John");
        signupRequest.setLastName("Doe");
        signupRequest.setPassword("password123");

        // Act
        String toString = signupRequest.toString();

        // Assert
        assertTrue(toString.contains("email=test@test.com"));
        assertTrue(toString.contains("firstName=John"));
        assertTrue(toString.contains("lastName=Doe"));
        assertTrue(toString.contains("password=password123"));
    }
}