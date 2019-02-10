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
import com.neo.booyah.server.entity.Customer;
import com.neo.booyah.server.entity.Show;
import com.neo.booyah.server.entity.Watchlist;
import com.neo.booyah.server.entity.WatchlistDTO;
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
   
   
}