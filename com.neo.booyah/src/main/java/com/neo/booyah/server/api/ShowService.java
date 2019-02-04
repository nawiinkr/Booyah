package com.neo.booyah.server.api;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.GenericEntity;
import org.json.simple.JSONObject;

import com.neo.booyah.server.dao.ShowsDao;
import com.neo.booyah.server.entity.Show;
import com.neo.booyah.server.entity.ShowDTO;
@Path("/ShowService") 

public class ShowService {  
   ShowsDao showsDao = new ShowsDao();  
   @POST 
   @Path("/shows") 
   @Consumes(MediaType.APPLICATION_JSON)
   @Produces(MediaType.APPLICATION_JSON) 
   public Response getShows(JSONObject inputJsonObj, @Context HttpServletRequest request, @DefaultValue("-1") @QueryParam("max") int max, @DefaultValue("-1") @QueryParam("skip") int  skip){ 
	   String user = (String) inputJsonObj.get("user");
	   String searchTerm = (String) inputJsonObj.get("query");
	   return Response.ok(SessionService.checkSession(request, user) ? showsDao.getAllShowsWithFavorites(max, skip, searchTerm, request)  : showsDao.getAllShows(max, skip, searchTerm)).build();
	   //List<Show> result = showsDao.getAllShows(max, skip, searchTerm);
	   //return  Response.ok(result).build();
   }
   
   @GET 
   @Path("/shows/{id}") 
   @Produces(MediaType.APPLICATION_JSON) 
   public List<Show> getShowDetails(@PathParam("id") String param){ 
      return showsDao.getShowDetails(param); 
   }
   
   @GET 
   @Path("/addShows") 
   @Produces(MediaType.TEXT_PLAIN) 
   public String addShows(){ 
      return showsDao.addShows(); 
   }
   
   @GET 
   @Path("/clearShows") 
   @Produces(MediaType.TEXT_PLAIN) 
   public String clearShows(){ 
      return showsDao.clearShows(); 
   }
   
}