package com.openclassrooms.starterjwt.controllers;

import com.openclassrooms.starterjwt.mapper.UserMapper;
import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@SpringBootTest
public class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    @Mock
    private UserMapper userMapper;

    @Mock
    private SecurityContext securityContext;

    @Mock
    private Authentication authentication;

    @Mock
    private UserDetails userDetails;

    private User mockUser;

    @BeforeEach
    void setUp() {
        // Configuration initiale des mocks
        mockUser = new User();
        mockUser.setId(1L);
        mockUser.setEmail("test@test.com");
        mockUser.setPassword("password");
        mockUser.setFirstName("John");
        mockUser.setLastName("Doe");

        // Configuration du SecurityContext
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(authentication.getPrincipal()).thenReturn(userDetails);
    }

    // Tests pour findById

    @Test
    void findById_ValidId_ReturnsUser() {
        // Arrange
        when(userService.findById(1L)).thenReturn(mockUser);

        // Act
        ResponseEntity<?> response = userController.findById("1");

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        verify(userService).findById(1L);
    }

    @Test
    void findById_InvalidId_ReturnsBadRequest() {
        // Act
        ResponseEntity<?> response = userController.findById("invalid");

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void findById_NonExistentId_ReturnsNotFound() {
        // Arrange
        when(userService.findById(999L)).thenReturn(null);

        // Act
        ResponseEntity<?> response = userController.findById("999");

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    // Tests pour delete

    @Test
    void delete_ValidIdAndAuthorizedUser_ReturnsOk() {
        // Arrange
        when(userService.findById(1L)).thenReturn(mockUser);
        when(userDetails.getUsername()).thenReturn("test@test.com");

        // Act
        ResponseEntity<?> response = userController.save("1");

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        verify(userService).delete(1L);
    }

    @Test
    void delete_UnauthorizedUser_ReturnsUnauthorized() {
        // Arrange
        when(userService.findById(1L)).thenReturn(mockUser);
        when(userDetails.getUsername()).thenReturn("other@test.com");

        // Act
        ResponseEntity<?> response = userController.save("1");

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
        verify(userService, never()).delete(1L);
    }

    @Test
    void delete_InvalidId_ReturnsBadRequest() {
        // Act
        ResponseEntity<?> response = userController.save("invalid");

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        verify(userService, never()).delete(anyLong());
    }

    @Test
    void delete_NonExistentId_ReturnsNotFound() {
        // Arrange
        when(userService.findById(999L)).thenReturn(null);

        // Act
        ResponseEntity<?> response = userController.save("999");

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        verify(userService, never()).delete(999L);
    }
}