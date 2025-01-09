package com.openclassrooms.starterjwt.IT;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

public class SessionControllerIntegrationTest extends AuthIntegrationTest {

    private String[] createSession() throws Exception {
        String response = LoginTestUser();
        String token = extractTokenFromResponse(response);

        String sessionJson = String.format(
                "{\"name\":\"%s\", \"description\":\"%s\", \"teacher_id\":%d, \"date\":\"%s\"}",
                "Test Session", "Description of the session", 1, "2025-01-09T10:00:00");

        String sessionResponse = mockMvc.perform(post("/api/session")
                .contentType(MediaType.APPLICATION_JSON)
                .content(sessionJson)
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        return new String[] { token, sessionResponse };
    }

    private String extractTokenFromResponse(String response) {
        return response.substring(response.indexOf("token\":\"") + 8,
                response.indexOf("\"", response.indexOf("token\":\"") + 8));
    }

    @Test
    void testCreateSession() throws Exception {
        createSession();
    }

    @Test
    void testFindById() throws Exception {
        String[] createdSessionResponse = createSession();
        String token = createdSessionResponse[0];
        Long sessionId = extractIdFromResponse(createdSessionResponse[1]);

        mockMvc.perform(get("/api/session/" + sessionId)
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());
    }

    @Test
    void testFindAll() throws Exception {
        String response = LoginTestUser();
        String token = extractTokenFromResponse(response);

        mockMvc.perform(get("/api/session")
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());
    }

    @Test
    void testUpdateSession() throws Exception {
        String[] createdSessionResponse = createSession();
        String token = createdSessionResponse[0];
        Long sessionId = extractIdFromResponse(createdSessionResponse[1]);

        String updatedSessionJson = String.format(
                "{\"name\":\"%s\", \"description\":\"%s\", \"teacher_id\":%d, \"date\":\"%s\"}",
                "Updated Session", "Updated description", 1, "2025-01-10T10:00:00");

        mockMvc.perform(put("/api/session/" + sessionId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(updatedSessionJson)
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());
    }

    @Test
    void testDeleteSession() throws Exception {
        String[] createdSessionResponse = createSession();
        String token = createdSessionResponse[0];
        Long sessionId = extractIdFromResponse(createdSessionResponse[1]);

        mockMvc.perform(delete("/api/session/" + sessionId)
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());
    }

    @Test
    void testParticipateInSession() throws Exception {
        String[] createdSessionResponse = createSession();
        String token = createdSessionResponse[0];
        Long sessionId = extractIdFromResponse(createdSessionResponse[1]);
        Long userId = 1L;

        mockMvc.perform(post("/api/session/" + sessionId + "/participate/" + userId)
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());
    }

    private Long extractIdFromResponse(String response) {
        return Long.valueOf(response.substring(response.indexOf("\"id\":") + 5,
                response.indexOf(",", response.indexOf("\"id\":") + 5)));
    }

}