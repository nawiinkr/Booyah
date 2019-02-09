package com.neo.booyah.server.api;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.json.simple.JSONObject;

import com.neo.booyah.server.dao.WatchlistDao;
import com.neo.booyah.server.entity.Watchlist;
@Path("/Watchlist") 

public class WatchlistService {  
   WatchlistDao watchlistDao = new WatchlistDao();  
   @POST 
   @Path("/create")
   @Consumes(MediaType.APPLICATION_JSON)
   @Produces(MediaType.APPLICATION_JSON) 
   public Response createWatchlist(JSONObject inputJsonObj, @Context HttpServletRequest request){ 
      String id = watchlistDao.createWatchlist(inputJsonObj, request);
	   return Response.ok(id).build();
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
      return watchlistDao.returnWatchlist(inputJsonObj); 
   }
   
   
}