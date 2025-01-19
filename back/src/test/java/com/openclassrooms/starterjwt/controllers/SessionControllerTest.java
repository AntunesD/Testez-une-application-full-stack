package com.openclassrooms.starterjwt.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.starterjwt.dto.SessionDto;
import com.openclassrooms.starterjwt.mapper.SessionMapper;
import com.openclassrooms.starterjwt.models.Session;
import com.openclassrooms.starterjwt.models.Teacher;
import com.openclassrooms.starterjwt.services.SessionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Date;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Tests unitaires pour le SessionController
 * Vérifie les endpoints REST pour la gestion des sessions de yoga
 */
@SpringBootTest
@AutoConfigureMockMvc
public class SessionControllerTest {

    // ========= Injection des dépendances =========
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SessionService sessionService;

    @MockBean
    private SessionMapper sessionMapper;

    @Autowired
    private ObjectMapper objectMapper;

    // ========= Données de test =========
    private Session session;
    private SessionDto sessionDto;
    private Teacher teacher;

    /**
     * Configuration initiale avant chaque test
     * Prépare les objets Session, Teacher et SessionDto
     */
    @BeforeEach
    void setUp() {
        // Configuration du Teacher
        teacher = new Teacher();
        teacher.setId(1L);
        teacher.setLastName("Doe");
        teacher.setFirstName("John");

        // Configuration de la Session
        session = new Session();
        session.setId(1L);
        session.setName("Yoga Session");
        session.setDate(new Date());
        session.setDescription("Description test");
        session.setTeacher(teacher);

        // Configuration du DTO
        sessionDto = new SessionDto();
        sessionDto.setId(1L);
        sessionDto.setName("Yoga Session");
        sessionDto.setDate(new Date());
        sessionDto.setDescription("Description test");
        sessionDto.setTeacher_id(1L);
    }

    // ========= Tests de récupération (GET) =========

    // Test pour récupérer une session par ID
    @Test
    @WithMockUser
    void findById_ShouldReturnSession() throws Exception {
        when(sessionService.getById(1L)).thenReturn(session);
        when(sessionMapper.toDto(session)).thenReturn(sessionDto);

        mockMvc.perform(get("/api/session/1"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(sessionDto)));
    }

    // Test pour récupérer une session avec un ID invalide
    @Test
    @WithMockUser
    void findById_WithInvalidId_ShouldReturnNotFound() throws Exception {
        when(sessionService.getById(999L)).thenReturn(null);

        mockMvc.perform(get("/api/session/999"))
                .andExpect(status().isNotFound());
    }

    // Test pour récupérer une session avec un format d'ID invalide
    @Test
    @WithMockUser
    void findById_WithInvalidIdFormat_ShouldReturnBadRequest() throws Exception {
        mockMvc.perform(get("/api/session/invalid"))
                .andExpect(status().isBadRequest());
    }

    // Test pour récupérer toutes les sessions
    @Test
    @WithMockUser
    void findAll_ShouldReturnAllSessions() throws Exception {
        when(sessionService.findAll()).thenReturn(Arrays.asList(session));
        when(sessionMapper.toDto(Arrays.asList(session))).thenReturn(Arrays.asList(sessionDto));

        mockMvc.perform(get("/api/session"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(Arrays.asList(sessionDto))));
    }

    // ========= Tests de création (POST) =========

    // Test pour créer une nouvelle session
    @Test
    @WithMockUser
    void create_ShouldReturnCreatedSession() throws Exception {
        when(sessionMapper.toEntity(any(SessionDto.class))).thenReturn(session);
        when(sessionService.create(any(Session.class))).thenReturn(session);
        when(sessionMapper.toDto(session)).thenReturn(sessionDto);

        mockMvc.perform(post("/api/session")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(sessionDto)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(sessionDto)));
    }

    // ========= Tests de mise à jour (PUT) =========

    // Test pour mettre à jour une session
    @Test
    @WithMockUser
    void update_ShouldReturnUpdatedSession() throws Exception {
        when(sessionMapper.toEntity(any(SessionDto.class))).thenReturn(session);
        when(sessionService.update(eq(1L), any(Session.class))).thenReturn(session);
        when(sessionMapper.toDto(session)).thenReturn(sessionDto);

        mockMvc.perform(put("/api/session/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(sessionDto)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(sessionDto)));
    }

    // Test pour mettre à jour une session avec un ID invalide
    @Test
    @WithMockUser
    void update_WithInvalidId_ShouldReturnBadRequest() throws Exception {
        mockMvc.perform(put("/api/session/invalid")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(sessionDto)))
                .andExpect(status().isBadRequest());
    }

    // ========= Tests de suppression (DELETE) =========

    // Test pour supprimer une session
    @Test
    @WithMockUser
    void delete_ShouldReturnOk() throws Exception {
        when(sessionService.getById(1L)).thenReturn(session);
        doNothing().when(sessionService).delete(1L);

        mockMvc.perform(delete("/api/session/1"))
                .andExpect(status().isOk());
    }

    // Test pour supprimer une session avec un ID invalide
    @Test
    @WithMockUser
    void delete_WithInvalidId_ShouldReturnNotFound() throws Exception {
        when(sessionService.getById(999L)).thenReturn(null);

        mockMvc.perform(delete("/api/session/999"))
                .andExpect(status().isNotFound());
    }

    // Test pour supprimer une session avec un format d'ID invalide
    @Test
    @WithMockUser
    void delete_WithInvalidIdFormat_ShouldReturnBadRequest() throws Exception {
        mockMvc.perform(delete("/api/session/invalid"))
                .andExpect(status().isBadRequest());
    }

    // ========= Tests de participation =========

    // Test pour participer à une session
    @Test
    @WithMockUser
    void participate_ShouldReturnOk() throws Exception {
        doNothing().when(sessionService).participate(1L, 1L);

        mockMvc.perform(post("/api/session/1/participate/1"))
                .andExpect(status().isOk());
    }

    // Test pour participer à une session avec un ID de session invalide
    @Test
    @WithMockUser
    void participate_WithInvalidSessionId_ShouldReturnBadRequest() throws Exception {
        mockMvc.perform(post("/api/session/invalid/participate/1"))
                .andExpect(status().isBadRequest());
    }

    // Test pour participer à une session avec un ID d'utilisateur invalide
    @Test
    @WithMockUser
    void participate_WithInvalidUserId_ShouldReturnBadRequest() throws Exception {
        mockMvc.perform(post("/api/session/1/participate/invalid"))
                .andExpect(status().isBadRequest());
    }

    // ========= Tests d'annulation de participation =========

    // Test pour ne plus participer à une session
    @Test
    @WithMockUser
    void noLongerParticipate_ShouldReturnOk() throws Exception {
        doNothing().when(sessionService).noLongerParticipate(1L, 1L);

        mockMvc.perform(delete("/api/session/1/participate/1"))
                .andExpect(status().isOk());
    }

    // Test pour ne plus participer à une session avec un ID de session invalide
    @Test
    @WithMockUser
    void noLongerParticipate_WithInvalidSessionId_ShouldReturnBadRequest() throws Exception {
        mockMvc.perform(delete("/api/session/invalid/participate/1"))
                .andExpect(status().isBadRequest());
    }

    // Test pour ne plus participer à une session avec un ID d'utilisateur invalide
    @Test
    @WithMockUser
    void noLongerParticipate_WithInvalidUserId_ShouldReturnBadRequest() throws Exception {
        mockMvc.perform(delete("/api/session/1/participate/invalid"))
                .andExpect(status().isBadRequest());
    }

    // ========= Tests d'authentification =========

    // Test pour vérifier l'accès sans authentification
    @Test
    void withoutAuthentication_ShouldReturnUnauthorized() throws Exception {
        mockMvc.perform(get("/api/session/1"))
                .andExpect(status().isUnauthorized());
    }
}