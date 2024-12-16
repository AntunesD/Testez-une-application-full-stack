package com.openclassrooms.starterjwt.controllers;

import com.openclassrooms.starterjwt.mapper.TeacherMapper;
import com.openclassrooms.starterjwt.models.Teacher;
import com.openclassrooms.starterjwt.services.TeacherService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
public class TeacherControllerTest {

    @Mock
    private TeacherService teacherService;

    @Mock
    private TeacherMapper teacherMapper;

    @InjectMocks
    private TeacherController teacherController;

    private Teacher teacher;

    @BeforeEach
    void setUp() {
        // Configuration initiale pour chaque test
        teacher = new Teacher();
        teacher.setId(1L);
        teacher.setFirstName("John");
        teacher.setLastName("Doe");
    }

    /**
     * Test de la méthode findById avec un ID valide
     * Vérifie que la méthode renvoie bien un enseignant existant
     */
    @Test
    void findById_ShouldReturnTeacher_WhenValidId() {
        // Arrange
        when(teacherService.findById(1L)).thenReturn(teacher);
        when(teacherMapper.toDto(teacher)).thenReturn(null); // Le DTO n'est pas important pour ce test

        // Act
        ResponseEntity<?> response = teacherController.findById("1");

        // Assert
        assertTrue(response.getStatusCode().is2xxSuccessful());
        verify(teacherService, times(1)).findById(1L);
        verify(teacherMapper, times(1)).toDto(teacher);
    }

    /**
     * Test de la méthode findById avec un ID invalide
     * Vérifie que la méthode renvoie une erreur 400 Bad Request
     */
    @Test
    void findById_ShouldReturnBadRequest_WhenInvalidId() {
        // Act
        ResponseEntity<?> response = teacherController.findById("invalid");

        // Assert
        assertTrue(response.getStatusCode().is4xxClientError());
        verify(teacherService, never()).findById(anyLong());
    }

    /**
     * Test de la méthode findById avec un ID non existant
     * Vérifie que la méthode renvoie une erreur 404 Not Found
     */
    @Test
    void findById_ShouldReturnNotFound_WhenTeacherDoesNotExist() {
        // Arrange
        when(teacherService.findById(999L)).thenReturn(null);

        // Act
        ResponseEntity<?> response = teacherController.findById("999");

        // Assert
        assertTrue(response.getStatusCode().is4xxClientError());
        verify(teacherService, times(1)).findById(999L);
    }

    /**
     * Test de la méthode findAll
     * Vérifie que la méthode renvoie bien la liste de tous les enseignants
     */
    @Test
    void findAll_ShouldReturnAllTeachers() {
        // Arrange
        List<Teacher> teachers = Arrays.asList(teacher);
        when(teacherService.findAll()).thenReturn(teachers);
        when(teacherMapper.toDto(teachers)).thenReturn(null); // Le DTO n'est pas important pour ce test

        // Act
        ResponseEntity<?> response = teacherController.findAll();

        // Assert
        assertTrue(response.getStatusCode().is2xxSuccessful());
        verify(teacherService, times(1)).findAll();
        verify(teacherMapper, times(1)).toDto(teachers);
    }
}