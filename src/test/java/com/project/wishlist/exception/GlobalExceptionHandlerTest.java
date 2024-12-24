package com.project.wishlist.exception;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler globalExceptionHandler = new GlobalExceptionHandler();

    @Test
    void handleRuntimeExceptionReturnsBadRequest() {
        RuntimeException ex = new RuntimeException("Runtime exception occurred");
        WebRequest request = mock(WebRequest.class);

        ResponseEntity<String> response = globalExceptionHandler.handleRuntimeException(ex, request);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Runtime exception occurred", response.getBody());
    }

    @Test
    void handleExceptionReturnsInternalServerError() {
        Exception ex = new Exception("General exception occurred");
        WebRequest request = mock(WebRequest.class);

        ResponseEntity<String> response = globalExceptionHandler.handleException(ex, request);

        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("General exception occurred", response.getBody());
    }

    @Test
    void handleNoResourceFoundExceptionReturnsNotFound() {
        NoResourceFoundException ex = new NoResourceFoundException(HttpMethod.GET, "Resource not found");

        ResponseEntity<String> response = globalExceptionHandler.handleNoResourceFoundException(ex);

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Resource not found", response.getBody());
    }
}