package com.openclassrooms.starterjwt.IT;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

public class SessionControllerIntegrationTest extends AuthIntegrationTest {

    @Test
    void testCreateSession() throws Exception {
        String response = LoginTestUser();
        String token = extractTokenFromResponse(response);

        String sessionJson = "{\"name\":\"Test Session\", \"description\":\"Description of the session\", \"teacher_id\":1, \"date\":\"2025-01-09T10:00:00\"}";

        mockMvc.perform(post("/api/session")
                .contentType(MediaType.APPLICATION_JSON)
                .content(sessionJson)
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());
    }

    private String extractTokenFromResponse(String response) {
        return response.substring(response.indexOf("token\":\"") + 8,
                response.indexOf("\"", response.indexOf("token\":\"") + 8));
    }
}