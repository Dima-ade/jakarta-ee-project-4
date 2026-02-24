package org.example.mapper;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.example.exception.ItemNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Provider
public class ItemNotFoundExceptionMapper implements ExceptionMapper<ItemNotFoundException> {

    private static final Logger logger = LoggerFactory.getLogger(ItemNotFoundExceptionMapper.class);

    public ItemNotFoundExceptionMapper() {
        System.out.println("\n\n\t\t======================"+getClass().getName()+"\n\n");
    }

    public Response toResponse(ItemNotFoundException exception) {
        System.out.println("ITEM NOT FOUND EXCEPTION");
        logger.warn("ITEM NOT FOUND EXCEPTION");
        return Response.status(404, exception.getMessage()).build();
    }
}