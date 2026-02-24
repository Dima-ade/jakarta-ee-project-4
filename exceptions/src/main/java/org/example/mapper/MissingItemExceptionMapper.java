package org.example.mapper;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import org.example.exception.MissingItemException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//@Provider
public class MissingItemExceptionMapper implements ExceptionMapper<MissingItemException> {

    private static final Logger logger = LoggerFactory.getLogger(MissingItemExceptionMapper.class);
    //private static final Logger logger = Logger.getLogger(MissingItemExceptionMapper.class.getName());

    public MissingItemExceptionMapper() {
        System.out.println("\n\n\t\t====================== constructor =>"+getClass().getName()+"\n\n");
    }

    public Response toResponse(MissingItemException exception) {
        Response response = Response.status(Response.Status.NOT_FOUND.getStatusCode(), exception.getMessage()).build();
        logger.info("MISSING ITEM EXCEPTION");
        System.out.println(getClass().getName()+" before return");
        return response;
    }
}