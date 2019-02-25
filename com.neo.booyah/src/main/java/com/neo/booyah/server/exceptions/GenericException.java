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
	public GenericException() {
		super("Internal server Exception happened");
	}

	public GenericException(String string) {
		super(string);
		this.message = string;
	}

	@Override
	public Response toResponse(GenericException exception)
	{
		ExceptionResponse e = new ExceptionResponse(Status.BAD_REQUEST.getStatusCode(), Status.BAD_REQUEST.toString(), exception.getMessage());
		return Response.status(Status.BAD_REQUEST).entity(e).type("application/json").build();
	}

	public String getMessage() {
		return message;
	}
}