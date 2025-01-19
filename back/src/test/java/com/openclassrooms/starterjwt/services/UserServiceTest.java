package com.openclassrooms.starterjwt.services;

import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Tests unitaires pour le UserService
 */
public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        user = new User();
        user.setId(1L);
        user.setEmail("test@test.com");
    }

    // Test pour vérifier que la méthode delete appelle la méthode deleteById du
    // repository
    @Test
    void delete_ShouldCallRepositoryDelete() {
        userService.delete(1L);
        verify(userRepository).deleteById(1L);
    }

    // Test pour vérifier que findById retourne un utilisateur existant
    @Test
    void findById_WhenUserExists_ShouldReturnUser() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        User result = userService.findById(1L);

        assertNotNull(result);
        assertEquals(user.getId(), result.getId());
    }

    // Test pour vérifier que findById retourne null si l'utilisateur n'existe pas
    @Test
    void findById_WhenUserDoesNotExist_ShouldReturnNull() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        User result = userService.findById(1L);

        assertNull(result);
    }
}