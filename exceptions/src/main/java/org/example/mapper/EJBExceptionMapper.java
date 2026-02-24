package org.example.mapper;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

@Provider
public class EJBExceptionMapper implements ExceptionMapper<Exception> {

    private static final Logger logger = LoggerFactory.getLogger(EJBExceptionMapper.class);

    public EJBExceptionMapper() {
        System.out.println("\n\n\t\t==========================================================EJBExceptionMapper.EJBExceptionMapper\n\n");
    }

    public Response toResponse(Exception exception) {
        System.out.println("INTERNAL SERVER ERROR");
        logger.warn("INTERNAL SERVER ERROR " + Arrays.toString(exception.getStackTrace()) + " " + exception.getMessage());
        return Response.status(500).build();
    }
}