package com.openclassrooms.starterjwt.mapper;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import com.openclassrooms.starterjwt.dto.TeacherDto;
import com.openclassrooms.starterjwt.models.Teacher;

import java.util.Arrays;
import java.util.List;

/**
 * Tests unitaires pour TeacherMapper
 * Vérifie la conversion entre les objets Teacher et TeacherDto
 */
public class TeacherMapperTest {

    private TeacherMapper teacherMapper = Mappers.getMapper(TeacherMapper.class);

    /**
     * Test la conversion d'un Teacher vers un TeacherDto
     * Vérifie que tous les champs sont correctement mappés
     */
    @Test
    public void shouldMapTeacherToTeacherDto() {
        // Arrange
        Teacher teacher = new Teacher();
        teacher.setId(1L);
        teacher.setFirstName("John");
        teacher.setLastName("Doe");

        // Act
        TeacherDto teacherDto = teacherMapper.toDto(teacher);

        // Assert
        assertEquals(teacher.getId(), teacherDto.getId());
        assertEquals(teacher.getFirstName(), teacherDto.getFirstName());
        assertEquals(teacher.getLastName(), teacherDto.getLastName());
    }

    /**
     * Test la conversion d'un TeacherDto vers un Teacher
     * Vérifie que tous les champs sont correctement mappés
     */
    @Test
    public void shouldMapTeacherDtoToTeacher() {
        // Arrange
        TeacherDto teacherDto = new TeacherDto();
        teacherDto.setId(1L);
        teacherDto.setFirstName("John");
        teacherDto.setLastName("Doe");

        // Act
        Teacher teacher = teacherMapper.toEntity(teacherDto);

        // Assert
        assertEquals(teacherDto.getId(), teacher.getId());
        assertEquals(teacherDto.getFirstName(), teacher.getFirstName());
        assertEquals(teacherDto.getLastName(), teacher.getLastName());
    }

    /**
     * Test la conversion d'une liste de Teacher vers une liste de TeacherDto
     * Vérifie que tous les éléments sont correctement mappés
     */
    @Test
    public void shouldMapTeacherListToTeacherDtoList() {
        // Arrange
        Teacher teacher1 = new Teacher();
        teacher1.setId(1L);
        teacher1.setFirstName("John");
        teacher1.setLastName("Doe");

        Teacher teacher2 = new Teacher();
        teacher2.setId(2L);
        teacher2.setFirstName("Jane");
        teacher2.setLastName("Doe");

        List<Teacher> teachers = Arrays.asList(teacher1, teacher2);

        // Act
        List<TeacherDto> teacherDtos = teacherMapper.toDto(teachers);

        // Assert
        assertEquals(2, teacherDtos.size());
        assertEquals(teacher1.getId(), teacherDtos.get(0).getId());
        assertEquals(teacher1.getFirstName(), teacherDtos.get(0).getFirstName());
        assertEquals(teacher2.getId(), teacherDtos.get(1).getId());
        assertEquals(teacher2.getFirstName(), teacherDtos.get(1).getFirstName());
    }

    /**
     * Test la conversion d'une liste de TeacherDto vers une liste de Teacher
     * Vérifie que tous les éléments sont correctement mappés
     */
    @Test
    public void shouldMapTeacherDtoListToTeacherList() {
        // Arrange
        TeacherDto teacherDto1 = new TeacherDto();
        teacherDto1.setId(1L);
        teacherDto1.setFirstName("John");
        teacherDto1.setLastName("Doe");

        TeacherDto teacherDto2 = new TeacherDto();
        teacherDto2.setId(2L);
        teacherDto2.setFirstName("Jane");
        teacherDto2.setLastName("Doe");

        List<TeacherDto> teacherDtos = Arrays.asList(teacherDto1, teacherDto2);

        // Act
        List<Teacher> teachers = teacherMapper.toEntity(teacherDtos);

        // Assert
        assertEquals(2, teachers.size());
        assertEquals(teacherDto1.getId(), teachers.get(0).getId());
        assertEquals(teacherDto1.getFirstName(), teachers.get(0).getFirstName());
        assertEquals(teacherDto2.getId(), teachers.get(1).getId());
        assertEquals(teacherDto2.getFirstName(), teachers.get(1).getFirstName());
    }
}