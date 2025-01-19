package com.openclassrooms.starterjwt.services;

import com.openclassrooms.starterjwt.exception.BadRequestException;
import com.openclassrooms.starterjwt.exception.NotFoundException;
import com.openclassrooms.starterjwt.models.Session;
import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.repository.SessionRepository;
import com.openclassrooms.starterjwt.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Tests unitaires pour le SessionService
 */
public class SessionServiceTest {

    @InjectMocks
    private SessionService sessionService;

    @Mock
    private SessionRepository sessionRepository;

    @Mock
    private UserRepository userRepository;

    private Session session;
    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        user = new User();
        user.setId(1L);
        user.setEmail("test@test.com");

        session = new Session();
        session.setId(1L);
        session.setName("Test Session");
        session.setUsers(new ArrayList<>());
    }

    // Test pour vérifier la création d'une session
    @Test
    void create_ShouldReturnCreatedSession() {
        when(sessionRepository.save(any(Session.class))).thenReturn(session);

        Session result = sessionService.create(session);

        assertNotNull(result);
        assertEquals(session.getId(), result.getId());
        verify(sessionRepository).save(session);
    }

    // Test pour vérifier que la méthode delete appelle le repository
    @Test
    void delete_ShouldCallRepositoryDelete() {
        sessionService.delete(1L);
        verify(sessionRepository).deleteById(1L);
    }

    // Test pour vérifier que toutes les sessions sont retournées
    @Test
    void findAll_ShouldReturnAllSessions() {
        List<Session> sessions = Arrays.asList(session);
        when(sessionRepository.findAll()).thenReturn(sessions);

        List<Session> result = sessionService.findAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(sessionRepository).findAll();
    }

    // Test pour vérifier que la session est retournée si elle existe
    @Test
    void getById_WhenSessionExists_ShouldReturnSession() {
        when(sessionRepository.findById(1L)).thenReturn(Optional.of(session));

        Session result = sessionService.getById(1L);

        assertNotNull(result);
        assertEquals(session.getId(), result.getId());
    }

    // Test pour vérifier que null est retourné si la session n'existe pas
    @Test
    void getById_WhenSessionDoesNotExist_ShouldReturnNull() {
        when(sessionRepository.findById(1L)).thenReturn(Optional.empty());

        Session result = sessionService.getById(1L);

        assertNull(result);
    }

    // Test pour vérifier que l'utilisateur est ajouté à la session
    @Test
    void participate_WhenSessionAndUserExist_ShouldAddUserToSession() {
        when(sessionRepository.findById(1L)).thenReturn(Optional.of(session));
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        sessionService.participate(1L, 1L);

        assertTrue(session.getUsers().contains(user));
        verify(sessionRepository).save(session);
    }

    // Test pour vérifier qu'une exception est levée si l'utilisateur participe déjà
    @Test
    void participate_WhenUserAlreadyParticipates_ShouldThrowBadRequestException() {
        session.getUsers().add(user);
        when(sessionRepository.findById(1L)).thenReturn(Optional.of(session));
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        assertThrows(BadRequestException.class, () -> sessionService.participate(1L, 1L));
    }

    // Test pour vérifier qu'une exception est levée si la session n'est pas trouvée
    @Test
    void participate_WhenSessionNotFound_ShouldThrowNotFoundException() {
        when(sessionRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> sessionService.participate(1L, 1L));
    }

    // Test pour vérifier que la session est mise à jour
    @Test
    void update_ShouldReturnUpdatedSession() {
        Session updatedSession = new Session();
        updatedSession.setId(1L);
        updatedSession.setName("Updated Session");

        when(sessionRepository.save(any(Session.class))).thenReturn(updatedSession);

        Session result = sessionService.update(1L, updatedSession);

        assertNotNull(result);
        assertEquals(updatedSession.getId(), result.getId());
        assertEquals(updatedSession.getName(), result.getName());
        verify(sessionRepository).save(updatedSession);
    }

    // Test pour vérifier que l'utilisateur est retiré de la session
    @Test
    void noLongerParticipate_WhenSessionAndUserExist_ShouldRemoveUserFromSession() {
        session.getUsers().add(user);
        when(sessionRepository.findById(1L)).thenReturn(Optional.of(session));

        sessionService.noLongerParticipate(1L, 1L);

        assertFalse(session.getUsers().contains(user));
        verify(sessionRepository).save(session);
    }

    // Test pour vérifier qu'une exception est levée si l'utilisateur ne participe
    // pas
    @Test
    void noLongerParticipate_WhenUserNotParticipating_ShouldThrowBadRequestException() {
        when(sessionRepository.findById(1L)).thenReturn(Optional.of(session));

        assertThrows(BadRequestException.class, () -> sessionService.noLongerParticipate(1L, 1L));
    }

    // Test pour vérifier qu'une exception est levée si la session n'est pas trouvée
    @Test
    void noLongerParticipate_WhenSessionNotFound_ShouldThrowNotFoundException() {
        when(sessionRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> sessionService.noLongerParticipate(1L, 1L));
    }
}