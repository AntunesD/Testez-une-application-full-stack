package com.openclassrooms.starterjwt.mapper;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import com.openclassrooms.starterjwt.dto.UserDto;
import com.openclassrooms.starterjwt.models.User;

import java.util.Arrays;
import java.util.List;

/**
 * Tests unitaires pour UserMapper
 * Vérifie la conversion entre les objets User et UserDto
 */
public class UserMapperTest {

    private UserMapper userMapper = Mappers.getMapper(UserMapper.class);

    /**
     * Test la conversion d'un User vers un UserDto
     * Vérifie que tous les champs sont correctement mappés
     */
    @Test
    public void shouldMapUserToUserDto() {
        // Arrange
        User user = new User();
        user.setId(1L);
        user.setEmail("test@test.com");
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setPassword("password");

        // Act
        UserDto userDto = userMapper.toDto(user);

        // Assert
        assertEquals(user.getId(), userDto.getId());
        assertEquals(user.getEmail(), userDto.getEmail());
        assertEquals(user.getFirstName(), userDto.getFirstName());
        assertEquals(user.getLastName(), userDto.getLastName());
        assertEquals(user.getPassword(), userDto.getPassword());
    }

    /**
     * Test la conversion d'un UserDto vers un User
     * Vérifie que tous les champs sont correctement mappés
     */
    @Test
    public void shouldMapUserDtoToUser() {
        // Arrange
        UserDto userDto = new UserDto();
        userDto.setId(1L);
        userDto.setEmail("test@test.com");
        userDto.setFirstName("John");
        userDto.setLastName("Doe");
        userDto.setPassword("password");

        // Act
        User user = userMapper.toEntity(userDto);

        // Assert
        assertEquals(userDto.getId(), user.getId());
        assertEquals(userDto.getEmail(), user.getEmail());
        assertEquals(userDto.getFirstName(), user.getFirstName());
        assertEquals(userDto.getLastName(), user.getLastName());
        assertEquals(userDto.getPassword(), user.getPassword());
    }

    /**
     * Test la conversion d'une liste de User vers une liste de UserDto
     * Vérifie que tous les éléments sont correctement mappés
     */
    @Test
    public void shouldMapUserListToUserDtoList() {
        // Arrange
        User user1 = new User();
        user1.setId(1L);
        user1.setEmail("test1@test.com");
        user1.setFirstName("John");
        user1.setLastName("Doe");
        user1.setPassword("password1");

        User user2 = new User();
        user2.setId(2L);
        user2.setEmail("test2@test.com");
        user2.setFirstName("Jane");
        user2.setLastName("Doe");
        user2.setPassword("password2");

        List<User> users = Arrays.asList(user1, user2);

        // Act
        List<UserDto> userDtos = userMapper.toDto(users);

        // Assert
        assertEquals(2, userDtos.size());
        assertEquals(user1.getId(), userDtos.get(0).getId());
        assertEquals(user1.getEmail(), userDtos.get(0).getEmail());
        assertEquals(user2.getId(), userDtos.get(1).getId());
        assertEquals(user2.getEmail(), userDtos.get(1).getEmail());
    }

    /**
     * Test la conversion d'une liste de UserDto vers une liste de User
     * Vérifie que tous les éléments sont correctement mappés
     */
    @Test
    public void shouldMapUserDtoListToUserList() {
        // Arrange
        UserDto userDto1 = new UserDto();
        userDto1.setId(1L);
        userDto1.setEmail("test1@test.com");
        userDto1.setFirstName("John");
        userDto1.setLastName("Doe");
        userDto1.setPassword("password1");

        UserDto userDto2 = new UserDto();
        userDto2.setId(2L);
        userDto2.setEmail("test2@test.com");
        userDto2.setFirstName("Jane");
        userDto2.setLastName("Doe");
        userDto2.setPassword("password2");

        List<UserDto> userDtos = Arrays.asList(userDto1, userDto2);

        // Act
        List<User> users = userMapper.toEntity(userDtos);

        // Assert
        assertEquals(2, users.size());
        assertEquals(userDto1.getId(), users.get(0).getId());
        assertEquals(userDto1.getEmail(), users.get(0).getEmail());
        assertEquals(userDto2.getId(), users.get(1).getId());
        assertEquals(userDto2.getEmail(), users.get(1).getEmail());
    }
}