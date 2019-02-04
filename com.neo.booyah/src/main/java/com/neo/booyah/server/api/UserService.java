package com.neo.booyah.server.api;

import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.json.simple.JSONObject;

import com.neo.booyah.server.dao.UserDao;
import com.neo.booyah.server.entity.Show;
import com.neo.booyah.server.entity.Watchlist;
@Path("/Users") 

public class UserService {  
   UserDao userDao = new UserDao();  
   @POST 
   @Path("/create")
   @Consumes(MediaType.APPLICATION_JSON)
   @Produces(MediaType.TEXT_PLAIN) 
   public String createUser(JSONObject inputJsonObj){ 
      return userDao.createUser(inputJsonObj); 
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
   public Collection<Watchlist> getWatchlists(@Context HttpServletRequest request){
	   Collection<Watchlist> wl = userDao.getWatchlists(request);
	   
	   return wl;
   }
   
   
}