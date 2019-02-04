package com.neo.booyah.server.api;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.json.simple.JSONObject;

import com.neo.booyah.server.dao.WatchlistDao;
import com.neo.booyah.server.entity.Watchlist;
@Path("/Watchlist") 

public class WatchlistService {  
   WatchlistDao watchlistDao = new WatchlistDao();  
   @POST 
   @Path("/create")
   @Consumes(MediaType.APPLICATION_JSON)
   @Produces(MediaType.TEXT_PLAIN) 
   public String createWatchlist(JSONObject inputJsonObj, @Context HttpServletRequest request){ 
      return watchlistDao.createWatchlist(inputJsonObj, request); 
   }
   
   @POST
   @Path("/addShow")
   @Consumes(MediaType.APPLICATION_JSON)
   @Produces(MediaType.TEXT_PLAIN) 
   public String addFavorite(JSONObject inputJsonObj, @Context HttpServletRequest request){  
      return watchlistDao.addShow(inputJsonObj, request); 
   }
   
   @POST
   @Path("/get")
   @Consumes(MediaType.APPLICATION_JSON)
   @Produces(MediaType.APPLICATION_JSON) 
   public Watchlist getWatchlist(JSONObject inputJsonObj, @Context HttpServletRequest request){  
      return watchlistDao.returnWatchlist(inputJsonObj, request); 
   }
   
   /*@POST
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
   }*/
   
   
}