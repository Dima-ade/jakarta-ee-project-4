package org.example.mapper;

import jakarta.persistence.EntityNotFoundException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Provider
public class EntityNotFoundMapper {

    public EntityNotFoundMapper() {
        System.out.println("\n\n\t\t************************************************************EntityNotFoundMapper.EntityNotFoundMapper\n\n");
    }

    private static final Logger logger = LoggerFactory.getLogger(EntityNotFoundMapper.class);
    public Response toResponse(EntityNotFoundException e) {
        logger.warn("NOT FOUND");
        return Response.status(Response.Status.NOT_FOUND).build();
    }
}
