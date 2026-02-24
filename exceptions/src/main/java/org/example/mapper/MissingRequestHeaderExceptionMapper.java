package org.example.mapper;

import jakarta.ws.rs.NotAuthorizedException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Provider
public class MissingRequestHeaderExceptionMapper implements ExceptionMapper<NotAuthorizedException> {

    private static final Logger logger = LoggerFactory.getLogger(MissingRequestHeaderExceptionMapper.class);

    public MissingRequestHeaderExceptionMapper() {
        System.out.println("\n\n\t\t==========================================================MissingRequestHeaderExceptionMapper.MissingRequestHeaderExceptionMapper\n\n");
    }

    public Response toResponse(NotAuthorizedException exception) {
        System.out.println("MISSING REQUEST HEADER EXCEPTION");
        logger.warn("MISSING REQUEST HEADER EXCEPTION");
        return Response.status(400).build();
    }
}