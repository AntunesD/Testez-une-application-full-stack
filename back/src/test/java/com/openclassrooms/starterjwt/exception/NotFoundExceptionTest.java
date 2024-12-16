package com.openclassrooms.starterjwt.exception;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class NotFoundExceptionTest {

    @Test
    public void shouldHaveCorrectHttpStatus() {
        // Arrange & Act
        ResponseStatus responseStatus = NotFoundException.class.getAnnotation(ResponseStatus.class);

        // Assert
        assertNotNull(responseStatus);
        assertEquals(HttpStatus.NOT_FOUND, responseStatus.value());
    }

    @Test
    public void shouldInheritFromRuntimeException() {
        // Arrange & Act
        NotFoundException exception = new NotFoundException();

        // Assert
        assertTrue(exception instanceof RuntimeException);
    }
}