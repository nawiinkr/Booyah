package com.neo.booyah.server.api;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.simple.JSONObject;

import com.neo.booyah.server.servlet.LoginServlet;
@Path("/auth") 

public class AuthService {
   @POST 
   @Path("/login")
   @Consumes(MediaType.APPLICATION_JSON)
   @Produces(MediaType.TEXT_PLAIN) 
   public String login(JSONObject inputJsonObj, @Context HttpServletRequest request){ 
      String user = (String) inputJsonObj.get("user");
      String password = (String) inputJsonObj.get("password");
	  
      if(LoginServlet.isValidUser(user, password)) {
    	  //create session here 
    	  return SessionService.createSession(request, user);
      }else {
    	 return "error";
      }
	  
   }
   
   @POST 
   @Path("/logout")
   @Consumes(MediaType.APPLICATION_JSON)
   @Produces(MediaType.TEXT_PLAIN) 
   public String logout(@Context HttpServletRequest request){ 
	   try {
	   SessionService.invalidateSession(request);
	   return "ok";
	   }catch(Exception e) {
		   return "error";
	   }
   }
   
   @POST 
   @Path("/checkSession")
   @Consumes(MediaType.APPLICATION_JSON)
   @Produces(MediaType.TEXT_PLAIN) 
   public String checkSession(JSONObject inputJsonObj, @Context HttpServletRequest request){ 
      String sessionIdParam = (String) inputJsonObj.get("user");
	  
      return SessionService.checkSession(request, sessionIdParam) ? "ok" : "Error";
	  
   }
   
   @GET
   @Path("/hello")
   public Response getMsg(@PathParam("name") String name) {
 
       String output = "Welcome   : " + name;
 
       return Response.status(200).entity(output).build();
 
   }
   
}