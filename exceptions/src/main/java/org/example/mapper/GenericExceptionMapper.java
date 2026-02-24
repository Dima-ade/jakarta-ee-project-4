package org.example.mapper;

import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Provider
public class GenericExceptionMapper implements ExceptionMapper<Throwable> {

    private static final Logger logger = LoggerFactory.getLogger(GenericExceptionMapper.class);

    @Override
    public Response toResponse(Throwable exception) {
        System.out.println("GENERIC EXCEPTION MAAPPER");
        logger.info("GENERIC EXCEPTION MAAPPER");

        return Response.status(500)
                .entity("Internal error: " + exception.getMessage())
                .type(MediaType.TEXT_PLAIN)
                .build();
    }
}
