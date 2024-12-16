package com.openclassrooms.starterjwt.mapper;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import com.openclassrooms.starterjwt.dto.SessionDto;
import com.openclassrooms.starterjwt.models.Session;
import com.openclassrooms.starterjwt.models.Teacher;
import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.services.TeacherService;
import com.openclassrooms.starterjwt.services.UserService;

/**
 * Tests unitaires pour SessionMapper
 * Vérifie la conversion entre les objets Session et SessionDto
 * Utilise Mockito pour simuler les services TeacherService et UserService
 */
@ExtendWith(MockitoExtension.class)
public class SessionMapperTest {

    @Mock
    private TeacherService teacherService;

    @Mock
    private UserService userService;

    @InjectMocks
    private SessionMapperImpl sessionMapper;

    /**
     * Configuration initiale avant chaque test
     * Injecte les services mockés dans le mapper
     */
    @BeforeEach
    public void setup() {
        ReflectionTestUtils.setField(sessionMapper, "teacherService", teacherService);
        ReflectionTestUtils.setField(sessionMapper, "userService", userService);
    }

    /**
     * Test la conversion d'une Session vers un SessionDto
     * Vérifie que tous les champs sont correctement mappés, y compris les relations
     */
    @Test
    public void shouldMapSessionToSessionDto() {
        // Arrange
        Teacher teacher = new Teacher();
        teacher.setId(1L);

        User user = new User();
        user.setId(1L);

        Session session = new Session();
        session.setId(1L);
        session.setName("Session 1");
        session.setDate(new Date());
        session.setDescription("Description");
        session.setTeacher(teacher);
        session.setUsers(Arrays.asList(user));

        // Act
        SessionDto sessionDto = sessionMapper.toDto(session);

        // Assert
        assertEquals(session.getId(), sessionDto.getId());
        assertEquals(session.getName(), sessionDto.getName());
        assertEquals(session.getDate(), sessionDto.getDate());
        assertEquals(session.getDescription(), sessionDto.getDescription());
        assertEquals(session.getTeacher().getId(), sessionDto.getTeacher_id());
        assertEquals(session.getUsers().get(0).getId(), sessionDto.getUsers().get(0));
    }

    /**
     * Test la conversion d'un SessionDto vers une Session
     * Vérifie que tous les champs sont correctement mappés
     * Vérifie que les services sont correctement utilisés pour résoudre les
     * relations
     */
    @Test
    public void shouldMapSessionDtoToSession() {
        // Arrange
        Teacher teacher = new Teacher();
        teacher.setId(1L);

        User user = new User();
        user.setId(1L);

        SessionDto sessionDto = new SessionDto();
        sessionDto.setId(1L);
        sessionDto.setName("Session 1");
        sessionDto.setDate(new Date());
        sessionDto.setDescription("Description");
        sessionDto.setTeacher_id(1L);
        sessionDto.setUsers(Arrays.asList(1L));

        when(teacherService.findById(1L)).thenReturn(teacher);
        when(userService.findById(1L)).thenReturn(user);

        // Act
        Session session = sessionMapper.toEntity(sessionDto);

        // Assert
        assertEquals(sessionDto.getId(), session.getId());
        assertEquals(sessionDto.getName(), session.getName());
        assertEquals(sessionDto.getDate(), session.getDate());
        assertEquals(sessionDto.getDescription(), session.getDescription());
        assertEquals(sessionDto.getTeacher_id(), session.getTeacher().getId());
        assertEquals(sessionDto.getUsers().get(0), session.getUsers().get(0).getId());
    }

    /**
     * Test la conversion d'une liste de Session vers une liste de SessionDto
     * Vérifie que tous les éléments sont correctement mappés
     */
    @Test
    public void shouldMapSessionListToSessionDtoList() {
        // Arrange
        Teacher teacher = new Teacher();
        teacher.setId(1L);

        User user = new User();
        user.setId(1L);

        Session session1 = new Session();
        session1.setId(1L);
        session1.setName("Session 1");
        session1.setDate(new Date());
        session1.setDescription("Description 1");
        session1.setTeacher(teacher);
        session1.setUsers(Arrays.asList(user));

        Session session2 = new Session();
        session2.setId(2L);
        session2.setName("Session 2");
        session2.setDate(new Date());
        session2.setDescription("Description 2");
        session2.setTeacher(teacher);
        session2.setUsers(Arrays.asList(user));

        List<Session> sessions = Arrays.asList(session1, session2);

        // Act
        List<SessionDto> sessionDtos = sessionMapper.toDto(sessions);

        // Assert
        assertEquals(2, sessionDtos.size());
        assertEquals(session1.getId(), sessionDtos.get(0).getId());
        assertEquals(session1.getName(), sessionDtos.get(0).getName());
        assertEquals(session2.getId(), sessionDtos.get(1).getId());
        assertEquals(session2.getName(), sessionDtos.get(1).getName());
    }

    /**
     * Test la conversion d'une liste de SessionDto vers une liste de Session
     * Vérifie que tous les éléments sont correctement mappés
     */
    @Test
    public void shouldMapSessionDtoListToSessionList() {
        // Arrange
        Teacher teacher = new Teacher();
        teacher.setId(1L);

        User user = new User();
        user.setId(1L);

        SessionDto sessionDto1 = new SessionDto();
        sessionDto1.setId(1L);
        sessionDto1.setName("Session 1");
        sessionDto1.setDate(new Date());
        sessionDto1.setDescription("Description 1");
        sessionDto1.setTeacher_id(1L);
        sessionDto1.setUsers(Arrays.asList(1L));

        SessionDto sessionDto2 = new SessionDto();
        sessionDto2.setId(2L);
        sessionDto2.setName("Session 2");
        sessionDto2.setDate(new Date());
        sessionDto2.setDescription("Description 2");
        sessionDto2.setTeacher_id(1L);
        sessionDto2.setUsers(Arrays.asList(1L));

        List<SessionDto> sessionDtos = Arrays.asList(sessionDto1, sessionDto2);

        when(teacherService.findById(1L)).thenReturn(teacher);
        when(userService.findById(1L)).thenReturn(user);

        // Act
        List<Session> sessions = sessionMapper.toEntity(sessionDtos);

        // Assert
        assertEquals(2, sessions.size());
        assertEquals(sessionDto1.getId(), sessions.get(0).getId());
        assertEquals(sessionDto1.getName(), sessions.get(0).getName());
        assertEquals(sessionDto2.getId(), sessions.get(1).getId());
        assertEquals(sessionDto2.getName(), sessions.get(1).getName());
    }
}