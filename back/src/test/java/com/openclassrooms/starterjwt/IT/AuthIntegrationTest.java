package com.openclassrooms.starterjwt.IT;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.http.MediaType;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class AuthIntegrationTest extends DatabaseIntegrationTest {

    @Autowired
    protected MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        // Créer des utilisateurs de test
        createUser("admin@test.com", "John", "Doe", "password123", false);
        createUser("user@test.com", "Jane", "Smith", "password456", false);
    }

    // Nouvelle méthode pour créer et connecter un utilisateur
    protected String LoginTestUser() throws Exception {
        // Préparer le JSON de connexion
        String loginJson = "{\"email\":\"admin@test.com\", \"password\":\"password123\"}";

        // Effectuer la connexion et récupérer le token
        return mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(loginJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").exists())
                .andExpect(jsonPath("$.username").value("admin@test.com"))
                .andExpect(jsonPath("$.message").doesNotExist())
                .andReturn()
                .getResponse()
                .getContentAsString();
    }

    @Test
    void testLoginTestUser() throws Exception {
        // Utiliser la méthode LoginTestUser pour se connecter
        String response = LoginTestUser();

        // Vérifier que la réponse contient le token
        assertNotNull(response);
    }
}