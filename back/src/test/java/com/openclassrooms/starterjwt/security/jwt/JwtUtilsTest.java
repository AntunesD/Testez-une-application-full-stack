package com.openclassrooms.starterjwt.security.jwt;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.test.util.ReflectionTestUtils;

import com.openclassrooms.starterjwt.security.services.UserDetailsImpl;

@ExtendWith(MockitoExtension.class)
public class JwtUtilsTest {

    @InjectMocks
    private JwtUtils jwtUtils;

    @Mock
    private Authentication authentication;

    @Mock
    private UserDetailsImpl userDetails;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(jwtUtils, "jwtSecret", "bezKoderSecretKey");
        ReflectionTestUtils.setField(jwtUtils, "jwtExpirationMs", 86400000);
    }

    // Test pour vérifier que le token JWT généré est valide
    @Test
    void generateJwtToken_ShouldReturnValidToken() {
        // Arrange
        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(userDetails.getUsername()).thenReturn("test@test.com");

        // Act
        String token = jwtUtils.generateJwtToken(authentication);

        // Assert
        assertNotNull(token);
        assertTrue(jwtUtils.validateJwtToken(token));
        assertEquals("test@test.com", jwtUtils.getUserNameFromJwtToken(token));
    }

    // Test pour valider un token JWT valide
    @Test
    void validateJwtToken_WithValidToken_ShouldReturnTrue() {
        // Arrange
        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(userDetails.getUsername()).thenReturn("test@test.com");
        String token = jwtUtils.generateJwtToken(authentication);

        // Act & Assert
        assertTrue(jwtUtils.validateJwtToken(token));
    }

    // Test pour valider un token JWT invalide
    @Test
    void validateJwtToken_WithInvalidToken_ShouldReturnFalse() {
        // Act & Assert
        assertFalse(jwtUtils.validateJwtToken("invalid.token.here"));
    }

    // Test pour valider un token JWT vide
    @Test
    void validateJwtToken_WithEmptyToken_ShouldReturnFalse() {
        // Act & Assert
        assertFalse(jwtUtils.validateJwtToken(""));
    }

    // Test pour valider un token JWT nul
    @Test
    void validateJwtToken_WithNullToken_ShouldReturnFalse() {
        // Act & Assert
        assertFalse(jwtUtils.validateJwtToken(null));
    }

    // Test pour obtenir le nom d'utilisateur à partir d'un token JWT valide
    @Test
    void getUserNameFromJwtToken_WithValidToken_ShouldReturnUsername() {
        // Arrange
        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(userDetails.getUsername()).thenReturn("test@test.com");
        String token = jwtUtils.generateJwtToken(authentication);

        // Act
        String username = jwtUtils.getUserNameFromJwtToken(token);

        // Assert
        assertEquals("test@test.com", username);
    }
}