package com.neo.booyah.server.dao;


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
import org.json.simple.JSONObject;

import com.neo.booyah.server.entity.Show;
import com.neo.booyah.server.entity.Watchlist;
import com.neo.booyah.server.utils.HibernateUtil;
import com.neo.booyah.server.entity.Customer;

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
	public static List<Customer> getUser(String email) {
		  List<Customer> customerList = null;
		  String queryString = "select * from Binged.Customer where email = :email";
		  
		  session = HibernateUtil.getSessionFactory().openSession();
	      session.beginTransaction();
	      
	      SQLQuery query = session.createSQLQuery(queryString);
	      
		  
		  query.setParameter("email", email);
		  query.addEntity(Customer.class);
		  
		  customerList = query.list();
		  
		  session.getTransaction().commit();
		  session.close();
		    
	      return  customerList;
	}
	
	public String addFavorite(JSONObject inputJsonObj, HttpServletRequest request) {
		ShowsDao showDao = new ShowsDao();
		
		String showId = (String)inputJsonObj.get("showId");
		String email = (String) request.getSession(false).getAttribute("username");
		
		Show show = (Show) showDao.getShowDetails(showId).get(0);
		Customer customer = getUser(email).get(0);
		
		customer.addFavoriteShow(show);
		
		session = HibernateUtil.getSessionFactory().openSession();
	    session.beginTransaction();
		
	    session.merge(customer);
		
	    session.getTransaction().commit();
	    session.close();
		
		return "ok";
	}

	public String removeFavorite(JSONObject inputJsonObj, HttpServletRequest request) {
		ShowsDao showDao = new ShowsDao();
		
		String showId = (String)inputJsonObj.get("showId");
		String email = (String) request.getSession(false).getAttribute("username");
		
		Show show = (Show) showDao.getShowDetails(showId).get(0);
		Customer customer = getUser(email).get(0);
		customer.removeFavoriteShow(show);
		
		session = HibernateUtil.getSessionFactory().openSession();
	    session.beginTransaction();
	    
		session.merge(customer);
		
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

	public Collection<Watchlist> getWatchlists(HttpServletRequest request) {
		String email = (String) request.getSession(false).getAttribute("username");
		
		 Customer customer = null;
		 
		  String queryString = "select * from Binged.Customer where email = :email";
		  
		  session = HibernateUtil.getSessionFactory().openSession();
	      session.beginTransaction();
	      
	      SQLQuery query = session.createSQLQuery(queryString);
	      
		  
		  query.setParameter("email", email);
		  query.addEntity(Customer.class);
		  
		  customer = (Customer) query.list().get(0);
		  
		  Collection<Watchlist> watchlists = customer.getWatchlists();
		  //session.getTransaction().commit();
		  //session.close();
		    
	      return  watchlists;
	}
}