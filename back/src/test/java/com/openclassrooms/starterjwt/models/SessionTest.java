package com.openclassrooms.starterjwt.models;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SessionTest {

    @Test
    void testSessionCreation() {
        // Arrange
        Session session = new Session();
        session.setId(1L);
        session.setName("Yoga Session");
        Date date = new Date();
        session.setDate(date);
        session.setDescription("Description test");

        Teacher teacher = new Teacher();
        teacher.setId(1L);
        session.setTeacher(teacher);

        List<User> users = new ArrayList<>();
        User user = new User();
        user.setId(1L);
        users.add(user);
        session.setUsers(users);

        LocalDateTime now = LocalDateTime.now();
        session.setCreatedAt(now);
        session.setUpdatedAt(now);

        // Assert
        assertEquals(1L, session.getId());
        assertEquals("Yoga Session", session.getName());
        assertEquals(date, session.getDate());
        assertEquals("Description test", session.getDescription());
        assertEquals(teacher, session.getTeacher());
        assertEquals(users, session.getUsers());
        assertEquals(now, session.getCreatedAt());
        assertEquals(now, session.getUpdatedAt());
    }

    @Test
    void testSessionBuilder() {
        // Arrange
        Date date = new Date();
        LocalDateTime now = LocalDateTime.now();
        Teacher teacher = new Teacher();
        List<User> users = new ArrayList<>();

        // Act
        Session session = Session.builder()
                .id(1L)
                .name("Yoga Session")
                .date(date)
                .description("Description test")
                .teacher(teacher)
                .users(users)
                .createdAt(now)
                .updatedAt(now)
                .build();

        // Assert
        assertEquals(1L, session.getId());
        assertEquals("Yoga Session", session.getName());
        assertEquals(date, session.getDate());
        assertEquals("Description test", session.getDescription());
        assertEquals(teacher, session.getTeacher());
        assertEquals(users, session.getUsers());
        assertEquals(now, session.getCreatedAt());
        assertEquals(now, session.getUpdatedAt());
    }

    @Test
    void testSessionEquality() {
        // Arrange
        Session session1 = new Session();
        session1.setId(1L);
        Session session2 = new Session();
        session2.setId(1L);
        Session session3 = new Session();
        session3.setId(2L);

        // Assert
        assertEquals(session1, session2);
        assertNotEquals(session1, session3);
        assertEquals(session1.hashCode(), session2.hashCode());
        assertNotEquals(session1.hashCode(), session3.hashCode());
    }
}