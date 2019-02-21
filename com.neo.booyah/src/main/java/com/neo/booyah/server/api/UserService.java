package com.neo.booyah.server.api;

import java.io.InputStream;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
//import org.glassfish.jersey.media.multipart.FormDataParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.json.simple.JSONObject;

import com.neo.booyah.server.dao.UserDao;
import com.neo.booyah.server.entity.Avatar;
import com.neo.booyah.server.entity.Customer;
import com.neo.booyah.server.entity.Show;
import com.neo.booyah.server.entity.Watchlist;
import com.neo.booyah.server.entity.WatchlistDTO;
import com.neo.booyah.server.exceptions.GenericException;

//import com.sun.jersey.core.header.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

@Path("/Users") 

public class UserService {  
   UserDao userDao = new UserDao();  
   @POST 
   @Path("/create")
   @Consumes(MediaType.APPLICATION_JSON)
   @Produces(MediaType.APPLICATION_JSON) 
   public Response createUser(JSONObject inputJsonObj) throws GenericException{ 
	   if(true) {
		   throw new GenericException("this is exception");
	   }
      return Response.ok(userDao.createUser(inputJsonObj), MediaType.TEXT_PLAIN).build();
   }
   
   @POST
   @Path("/manage/favorites/add")
   @Consumes(MediaType.APPLICATION_JSON)
   @Produces(MediaType.TEXT_PLAIN) 
   public String addFavorite(JSONObject inputJsonObj, @Context HttpServletRequest request){  
      return userDao.addFavorite(inputJsonObj, request); 
   }
   
   @POST
   @Path("/manage/favorites/remove")
   @Consumes(MediaType.APPLICATION_JSON)
   @Produces(MediaType.TEXT_PLAIN) 
   public String removeFavorite(JSONObject inputJsonObj, @Context HttpServletRequest request){  
      return userDao.removeFavorite(inputJsonObj, request); 
   }
   
   @GET
   @Path("/manage/favorites/getAllFavorites")
   @Consumes(MediaType.APPLICATION_JSON)
   @Produces(MediaType.APPLICATION_JSON) 
   public Collection<Show> getAllFavorites(@Context HttpServletRequest request){  
      return userDao.getAllFavorites(request); 
   }
   
   @POST
   @Path("/getWatchlists")
   @Consumes(MediaType.APPLICATION_JSON)
   @Produces(MediaType.APPLICATION_JSON) 
   public List<WatchlistDTO> getWatchlists(@Context HttpServletRequest request){
	   List<WatchlistDTO> wl = userDao.getWatchlists(request);
	   
	   return wl;
   }
   
   @GET
   @Path("/getUser")
   @Consumes(MediaType.APPLICATION_JSON)
   @Produces(MediaType.APPLICATION_JSON) 
   public Customer getUserDetails(@Context HttpServletRequest request){
	   String email = (String) request.getSession(false).getAttribute("username");
	   Customer result = UserDao.getUser(email);
	   
	   return result;
   }
   
   @POST
   @Path("/updateUser")
   @Consumes(MediaType.APPLICATION_JSON)
   @Produces(MediaType.TEXT_PLAIN) 
   public String updateUserDetails(JSONObject inputJsonObj){
	   
	   Customer result = UserDao.updateUser(inputJsonObj);
	   
	   return "ok";
   }
   
   @POST
   @Path("/upload")
   @Consumes(MediaType.MULTIPART_FORM_DATA)
   public Response updateProfileImage(@Context HttpServletRequest request, @FormDataParam("file") InputStream uploadedInputStream,  
           @FormDataParam("file") FormDataContentDisposition fileDetail){
	   
	   UserDao.updateProfilePic(request, uploadedInputStream, fileDetail);
	   
	   return Response.status(Status.OK).build();
   }
   
   @GET
   @Path("/getProfileImage")
   @Produces(MediaType.APPLICATION_JSON)
   public Avatar getProfileImage(@Context HttpServletRequest request){
	   
	   Avatar av = UserDao.getUserProfilePic(request);
	   
	   return av;
   }
   
   @GET
   @Path("/removeProfileImage")
   @Produces(MediaType.APPLICATION_JSON)
   public Response removeProfileImage(@Context HttpServletRequest request){
	   
	   UserDao.removeUserProfilePic(request);
	   
	   return Response.ok().build();
   }
   
   
   
}