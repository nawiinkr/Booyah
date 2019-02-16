package com.neo.booyah.server.dao;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;

import org.hibernate.FetchMode;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.json.simple.JSONObject;

import com.neo.booyah.server.entity.Show;
import com.neo.booyah.server.entity.Watchlist;
import com.neo.booyah.server.entity.WatchlistDTO;
import com.neo.booyah.server.utils.HibernateUtil;
//import com.sun.jersey.core.header.FormDataContentDisposition;
import com.neo.booyah.server.entity.Avatar;
import com.neo.booyah.server.entity.Customer;

import org.apache.commons.io.IOUtils;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

public class UserDao {
	   //private static EntityManagerFactory factory;
	   //private static final String PERSISTENCE_UNIT_NAME = "Binged";
	private static Session session;

	public String createUser(JSONObject inputJsonObj) {
		Customer user = new Customer();
		String name = (String)inputJsonObj.get("name");
		String email = (String)inputJsonObj.get("email");
		String dob = (String)inputJsonObj.get("dateOfBirth");
		String password = (String)inputJsonObj.get("password");
		
		password = getPasswordSalt(email, password);
		user.setName(name);
		user.setEmail(email);
		user.setDob(dob);
		user.setPasswordSalt(password);
		
		session = HibernateUtil.getSessionFactory().openSession();
    	session.beginTransaction();
    	
        session.save(user);

        session.getTransaction().commit();
        session.close();
		return "ok";
	}
	
	public boolean authenticateUser(String user, String pass) {
		
		String passwordSalt = getPasswordSalt(user, pass);
		String queryString = "select Name,Email,UserId from Binged.Customer where email = :email and passwordSalt = :passwordSalt";
		
		session = HibernateUtil.getSessionFactory().openSession();
    	session.beginTransaction();
    	
    	SQLQuery query = session.createSQLQuery(queryString);
    	
		
		
		query.setParameter("email", user);
		query.setParameter("passwordSalt", passwordSalt);
		//query.addEntity(Customer.class);
		
		@SuppressWarnings("unchecked")
		List<Customer> list = query.list();
		
		session.getTransaction().commit();
	    session.close();
	      
		return list.size() == 1;
	}
	
	public static String getPasswordSalt(String uname, String password) {
		String salt = "";
		String pass = uname + password;
		salt = md5(pass);
		return salt;
	}
	
	public static String md5(String input) {
		
		String md5 = null;
		
		if(null == input) return null;
		
		try {
			
		//Create MessageDigest object for MD5
		MessageDigest digest = MessageDigest.getInstance("MD5");
		
		//Update input string in message digest
		digest.update(input.getBytes(), 0, input.length());

		//Converts message digest value in base 16 (hex) 
		md5 = new BigInteger(1, digest.digest()).toString(16);

		} catch (NoSuchAlgorithmException e) {

			e.printStackTrace();
		}
		return md5;
	}
	
	@SuppressWarnings("unchecked")
	public static Customer getUser(String email) {
		  List<Customer> customerList = null;
		  String queryString = "select * from Binged.Customer where email = :email";
		  
		  session = HibernateUtil.getSessionFactory().openSession();
	      session.beginTransaction();
	      
	      SQLQuery query = session.createSQLQuery(queryString);
	      
		  
		  query.setParameter("email", email);
		  query.addEntity(Customer.class);
		  
		  customerList = query.list();
		  
		  Customer result = customerList.get(0);
		  
		  session.getTransaction().commit();
		  session.close();
		  
		  result.setPasswordSalt("");
		    
	      return  result;
	}
	
	public String addFavorite(JSONObject inputJsonObj, HttpServletRequest request) {
		ShowsDao showDao = new ShowsDao();
		
		String showId = (String)inputJsonObj.get("showId");
		String email = (String) request.getSession(false).getAttribute("username");
		
		Show show = showDao.getShowDetails(showId);
		String queryString = "select * from Binged.Customer where email = :email";
		  
		  session = HibernateUtil.getSessionFactory().openSession();
	      session.beginTransaction();
	      
	      SQLQuery query = session.createSQLQuery(queryString);
	      
		  
		  query.setParameter("email", email);
		  query.addEntity(Customer.class);
		  
		  Customer customer = (Customer) query.list().get(0);
		
		customer.addFavoriteShow(show);
		
	    session.save(customer);
		
	    session.getTransaction().commit();
	    session.close();
		
		return "ok";
	}

	public String removeFavorite(JSONObject inputJsonObj, HttpServletRequest request) {
		ShowsDao showDao = new ShowsDao();
		
		String showId = (String)inputJsonObj.get("showId");
		String email = (String) request.getSession(false).getAttribute("username");
		
		Show show = showDao.getShowDetails(showId);
		String queryString = "select * from Binged.Customer where email = :email";
		  
		  session = HibernateUtil.getSessionFactory().openSession();
	      session.beginTransaction();
	      
	      SQLQuery query = session.createSQLQuery(queryString);
	      
		  
		  query.setParameter("email", email);
		  query.addEntity(Customer.class);
		  
		  Customer customer = (Customer) query.list().get(0);
		
		customer.removeFavoriteShow(show);
		
	    session.save(customer);
		
	    session.getTransaction().commit();
	    session.close();
		
		return "ok";
	}

	public Collection<Show> getAllFavorites(HttpServletRequest request) {
		
		String email = (String) request.getSession(false).getAttribute("username");
		
		 Customer customer = null;
		 
		  String queryString = "select * from Binged.Customer where email = :email";
		  
		  session = HibernateUtil.getSessionFactory().openSession();
	      session.beginTransaction();
	      
	      SQLQuery query = session.createSQLQuery(queryString);
	      
		  
		  query.setParameter("email", email);
		  query.addEntity(Customer.class);
		  
		  customer = (Customer) query.list().get(0);
		  
		  Collection<Show> favouriteShows = customer.getFavoriteShows();
		  //session.getTransaction().commit();
		  //session.close();
		    
	      return  favouriteShows;
	}

	public List<WatchlistDTO> getWatchlists(HttpServletRequest request) {
		String email = (String) request.getSession(false).getAttribute("username");
		
		 Customer customer = null;
		 
		 customer = getUser(email);
		  
		  List<WatchlistDTO> watchlists = WatchlistDao.getWatchlistsByUser(customer.getUserId());
		    
	      return  watchlists;
	}

	public static Customer updateUser(JSONObject inputJsonObj) {
		
		String userId = (String) inputJsonObj.get("userId");
		String name = (String) inputJsonObj.get("name");
		String dob = (String) inputJsonObj.get("dob");
		
		
		session = HibernateUtil.getSessionFactory().openSession();
	    session.beginTransaction();
	    
	    Customer user = (Customer) session.get(Customer.class, userId);
	    
	    user.setName(name);
	    user.setDob(dob);
	    
	    session.saveOrUpdate(user);
	    
	    session.getTransaction().commit();
	    session.close();
	    return user;
		
	}
	
	
	public static String updateProfilePic(HttpServletRequest request, InputStream uploadedInputStream, FormDataContentDisposition fileDetail) {
		
		String email = (String) request.getSession(false).getAttribute("username");
		Customer user = getUser(email);
		int read = 0;
		OutputStream outpuStream = null;
        byte[] bytes = null;
		try {
			bytes = IOUtils.toByteArray(uploadedInputStream);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        
       
        
        session = HibernateUtil.getSessionFactory().openSession();
	    session.beginTransaction();
	    
	    @SuppressWarnings("unchecked")
		List<Avatar> exists =  session.createCriteria(Avatar.class).add(Restrictions.eq("userId", user.getUserId())).list();
	    Avatar av;
	    
	    if(exists.size() > 0) {
	    	av = exists.get(0);
	    	av.setAvatar(bytes);
	    }else {
	    	av = new Avatar();
		    av.setFilename(fileDetail.getFileName());
		    av.setAvatar(bytes);
		    av.setUserId(user.getUserId());
	    }
	    
	    
	    session.saveOrUpdate(av);
	    
	    session.getTransaction().commit();
	    session.close();
		return "ok";
	}

	public static Avatar getUserProfilePic(HttpServletRequest request) {
		String email = (String) request.getSession(false).getAttribute("username");
		Customer user = getUser(email);
		
		session = HibernateUtil.getSessionFactory().openSession();
	    session.beginTransaction();
	    
	    @SuppressWarnings("unchecked")
		List<Avatar> av = session.createCriteria(Avatar.class).add(Restrictions.eq("userId", user.getUserId())).list();
	    
	    session.getTransaction().commit();
	    session.close();
	    
	    return av.size() > 0 ? av.get(0) : new Avatar();
		
	}

	public static String removeUserProfilePic(HttpServletRequest request) {
		String email = (String) request.getSession(false).getAttribute("username");
		Customer user = getUser(email);
		
		session = HibernateUtil.getSessionFactory().openSession();
	    session.beginTransaction();
	    
	    String q = "delete from Binged.Avatar where userId= :id";
	    int del = session.createSQLQuery(q).setParameter("id", user.getUserId()).executeUpdate();
	    
		 session.getTransaction().commit();
		 session.close();
	    
	    return "ok";
	}
}