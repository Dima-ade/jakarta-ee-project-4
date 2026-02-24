package org.example.mapper;

import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Provider
public class ConstraintViolationxceptionMapper implements ExceptionMapper<ConstraintViolationException> {

    private static final Logger logger = LoggerFactory.getLogger(ConstraintViolationxceptionMapper.class);
    public ConstraintViolationxceptionMapper() {
        System.out.println("\n\n\t\t==========================================================ConstraintViolationxceptionMapper.ConstraintViolationxceptionMapper\n\n");
    }

    public Response toResponse(ConstraintViolationException exception) {
        System.out.println("CONSTRAINT VIOLATION EXCEPTION");
        logger.warn("CONSTRAINT VIOLATION EXCEPTION");
        return Response.status(400).build();
    }
}