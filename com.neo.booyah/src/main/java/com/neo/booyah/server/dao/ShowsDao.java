package com.neo.booyah.server.dao;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.neo.booyah.server.entity.Customer;
import com.neo.booyah.server.entity.Show;
import com.neo.booyah.server.entity.ShowDTO;
import com.neo.booyah.server.utils.HibernateUtil;


public class ShowsDao {
	   private Session session;
	   
	   //@SuppressWarnings("unchecked")
		@SuppressWarnings("unchecked")
		public List<ShowDTO> getAllShowsWithFavorites(int max, int skip, String searchTerm, HttpServletRequest request){
		   	List<ShowDTO>  showList = null; 
			  
			  String email = (String) request.getSession(false).getAttribute("username");
			  Customer customer = UserDao.getUser(email);
			  
			  String nativeQuery = "select * from binged.show as s left join (select ShowId as isFavorite from binged.favorite where UserId = \""+ customer.getUserId()+"\") as j on j.isFavorite=s.ShowId";
			  
			  session = HibernateUtil.getSessionFactory().openSession();
		   	  session.beginTransaction();
		   	  
		   	  
			  if(searchTerm != null && searchTerm != "") {
				  nativeQuery+=" where name like \"%"+searchTerm+"%\"";
			  }
			  
			  nativeQuery += " order by name";
			  SQLQuery q = session.createSQLQuery(nativeQuery);//em.createQuery("select t from Show t");
			  q.addEntity(ShowDTO.class);
			 
			  
			  if(max != -1) {
				  q.setMaxResults(max);
			  }
			  if(skip != -1) {
				q.setFirstResult(skip);  
			  }
			  
		      showList = q.list();
		      session.getTransaction().commit();
		      session.close();
		      
		      return  showList; 
		   }
	   
	   @SuppressWarnings("unchecked")
		public List<Show> getAllShows(int max, int skip, String searchTerm){
		   List<Show>  showList = null; 
		   	session = HibernateUtil.getSessionFactory().openSession();
	   		session.beginTransaction();
	   		
			  String nativeQuery = "select * from binged.show";
			  
			  if(searchTerm != null && searchTerm != "") {
				  nativeQuery+=" where name like \"%"+searchTerm+"%\"";
			  }
			  
			  nativeQuery += " order by name";
			  
			  SQLQuery q = session.createSQLQuery(nativeQuery);
			  q.addEntity(Show.class);
			 
			  
			  if(max != -1) {
				  q.setMaxResults(max);
			  }
			  if(skip != -1) {
				q.setFirstResult(skip);  
			  }
			  
		      showList = q.list();
		      session.getTransaction().commit();
		      session.close();
		      
		      return  showList; 
		   }

	@SuppressWarnings("unchecked")
	public Show getShowDetails(String param) {
		  Show show = null;
		  session = HibernateUtil.getSessionFactory().openSession();
	      session.beginTransaction();
	      
	      show = (Show) session.get(Show.class, param);
	      session.getTransaction().commit();
	      session.close();
	      
	      return  show;
	}

	public String addShows() {
		InputStream stream = getClass().getClassLoader().getResourceAsStream("shows.json");
		
		JSONArray obj = getJSONObjectsArray(stream);
    	session = HibernateUtil.getSessionFactory().openSession();
    	session.beginTransaction();
		
		
		String query = session.getNamedQuery("Show.findAll").toString();
		@SuppressWarnings("unchecked")
		List<Show> list = (List<Show>) session.createSQLQuery(query).list();
		
		if(list.size() > 0) {
			System.out.println("Already shows present");
			return "Already shows present";
		}
        
    	 for (Object o : obj)
    	  {
    	    Show temp = getShowPersistenceObject((JSONObject) o);
    	    session.save(temp);
    	  }
    	 
    	 session.getTransaction().commit();
    	 session.close();
    	 System.out.println("done!!!");
		return "ok";
	}
	
	public static Show getShowPersistenceObject(JSONObject obj) {
    	Show show = new Show();
    	
    	JSONObject imageObj = (JSONObject) obj.get("image");
    	show.setName((String) obj.get("name"));
    	show.setImageUrl((String)imageObj.get("medium"));
    	show.setLanguage((String) obj.get("language"));
    	show.setPremierDate((String) obj.get("premiered"));
    	show.setRunTime((Long) obj.get("runtime"));
    	show.setSummary((String) obj.get("summary"));
    	show.setType((String) obj.get("type"));
    	show.setUrl((String) obj.get("url"));
    	
		return show;
    }
    
    public static JSONArray getJSONObjectsArray(InputStream stream) {
    	try {
    		JSONParser jsonParser = new JSONParser();
    		JSONArray jsonObject = (JSONArray)jsonParser.parse(
    		      new InputStreamReader(stream, "UTF-8"));
	        return jsonObject;
    	 } catch (FileNotFoundException e) {
             e.printStackTrace();
         } catch (IOException e) {
             e.printStackTrace();
         } catch (ParseException e) {
             e.printStackTrace();
         }
		return null;
    }

	public String clearShows() {
		session = HibernateUtil.getSessionFactory().openSession();
    	session.beginTransaction();
    	
        session.createQuery("Delete From Show").executeUpdate();

        session.getTransaction().commit();
        session.close();
		return "ok";
	}
}