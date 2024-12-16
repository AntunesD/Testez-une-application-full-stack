package com.openclassrooms.starterjwt.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.starterjwt.payload.request.LoginRequest;
import com.openclassrooms.starterjwt.payload.request.SignupRequest;
import com.openclassrooms.starterjwt.repository.UserRepository;
import com.openclassrooms.starterjwt.security.jwt.JwtUtils;
import com.openclassrooms.starterjwt.security.services.UserDetailsImpl;
import com.openclassrooms.starterjwt.models.User;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @MockBean
  private AuthenticationManager authenticationManager;

  @MockBean
  private JwtUtils jwtUtils;

  @MockBean
  private PasswordEncoder passwordEncoder;

  @MockBean
  private UserRepository userRepository;

  @Test
  void testAuthenticateUserSuccess() throws Exception {
    // Mock des objets
    LoginRequest loginRequest = new LoginRequest();
    loginRequest.setEmail("admin@test.com");
    loginRequest.setPassword("password123");

    // Créer un UserDetailsImpl pour le test
    User testUser = new User("admin@test.com", "Doe", "John", "password123", false);
    testUser.setId(1L);
    UserDetailsImpl userDetails = UserDetailsImpl.builder()
        .id(testUser.getId())
        .username(testUser.getEmail())
        .firstName(testUser.getFirstName())
        .lastName(testUser.getLastName())
        .password(testUser.getPassword())
        .admin(testUser.isAdmin())
        .build();
    Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null);

    // Créer un User pour le test
    User user = new User();
    user.setEmail("admin@test.com");
    user.setAdmin(true);

    // Configuration des comportements mockés
    Mockito.when(authenticationManager.authenticate(Mockito.any())).thenReturn(authentication);
    Mockito.when(jwtUtils.generateJwtToken(authentication)).thenReturn("mocked-jwt-token");
    Mockito.when(userRepository.findByEmail("admin@test.com")).thenReturn(Optional.of(user));

    // Simuler la requête HTTP POST
    mockMvc.perform(post("/api/auth/login")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(loginRequest)))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(content().json(
            "{" +
                "\"token\":\"mocked-jwt-token\"," +
                "\"type\":\"Bearer\"," +
                "\"id\":1," +
                "\"username\":\"admin@test.com\"," +
                "\"firstName\":\"John\"," +
                "\"lastName\":\"Doe\"," +
                "\"admin\":true" +
                "}"));
  }

  @Test
  void testRegisterUserSuccess() throws Exception {
    // Mock des objets
    SignupRequest signupRequest = new SignupRequest();
    signupRequest.setEmail("newuser@test.com");
    signupRequest.setPassword("password123");
    signupRequest.setFirstName("John");
    signupRequest.setLastName("Doe");

    // Configuration des comportements mockés
    Mockito.when(userRepository.existsByEmail("newuser@test.com")).thenReturn(false);
    Mockito.when(passwordEncoder.encode("password123")).thenReturn("encoded-password");

    // Simuler la requête HTTP POST
    mockMvc.perform(post("/api/auth/register")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(signupRequest)))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(content().json("{\"message\":\"User registered successfully!\"}"));
  }

  @Test
  void testRegisterUserEmailAlreadyTaken() throws Exception {
    // Mock des objets
    SignupRequest signupRequest = new SignupRequest();
    signupRequest.setEmail("existinguser@test.com");
    signupRequest.setPassword("password123");
    signupRequest.setFirstName("John");
    signupRequest.setLastName("Doe");

    // Configuration des comportements mockés
    Mockito.when(userRepository.existsByEmail("existinguser@test.com")).thenReturn(true);

    // Simuler la requête HTTP POST
    mockMvc.perform(post("/api/auth/register")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(signupRequest)))
  
        .andExpect(status().isBadRequest())
        .andExpect(content().string("{\"message\":\"Error: Email is already taken!\"}"));
  }
}
