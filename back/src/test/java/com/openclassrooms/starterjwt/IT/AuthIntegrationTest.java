package com.openclassrooms.starterjwt.IT;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class AuthIntegrationTest extends DatabaseIntegrationTest {

    @Autowired
    protected MockMvc mockMvc;

    // Nouvelle méthode pour créer et connecter un utilisateur
    protected String LoginTestUser() throws Exception {
        // Préparer le JSON de connexion
        createUser("admin@test.com", "John", "Doe", "password123", false);
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

    // Ajout d'un test pour l'enregistrement d'un utilisateur
    @Test
    void testRegisterUser() throws Exception {
        // Préparer le JSON d'enregistrement
        String registerJson = "{\"email\":\"newuser@test.com\", \"password\":\"newpassword\", \"firstName\":\"New\", \"lastName\":\"User\"}";

        // Effectuer l'enregistrement
        mockMvc.perform(post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(registerJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("User registered successfully!"));
    }
}