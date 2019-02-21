package com.neo.booyah.server.exceptions;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
 
@Provider
public class GenericException extends Exception implements
                ExceptionMapper<GenericException>
{
    private static final long serialVersionUID = 1L;
    private String message;
    private String statusCode;
    public GenericException() {
        super("Internal server Exception happened");
    }
 
    public GenericException(String string) {
        super(string);
        this.message = string;
        this.statusCode = Status.BAD_REQUEST.toString();
    }
 
    @Override
    public Response toResponse(GenericException exception)
    {
        return Response.status(Status.BAD_REQUEST).entity(exception)
                                    .type("application/json").build();
    }
}