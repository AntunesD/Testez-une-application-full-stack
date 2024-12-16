package com.openclassrooms.starterjwt.exception;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class BadRequestExceptionTest {

    @Test
    public void shouldHaveCorrectHttpStatus() {
        // Arrange & Act
        ResponseStatus responseStatus = BadRequestException.class.getAnnotation(ResponseStatus.class);

        // Assert
        assertNotNull(responseStatus);
        assertEquals(HttpStatus.BAD_REQUEST, responseStatus.value());
    }

    @Test
    public void shouldInheritFromRuntimeException() {
        // Arrange & Act
        BadRequestException exception = new BadRequestException();

        // Assert
        assertTrue(exception instanceof RuntimeException);
    }
}