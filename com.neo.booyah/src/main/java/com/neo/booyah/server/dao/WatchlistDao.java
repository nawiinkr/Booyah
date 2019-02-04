package com.neo.booyah.server.dao;


import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONObject;

import com.neo.booyah.server.entity.Customer;
import com.neo.booyah.server.entity.Show;
import com.neo.booyah.server.entity.Watchlist;

public class WatchlistDao {
	   private static EntityManagerFactory factory;
	   private static final String PERSISTENCE_UNIT_NAME = "Binged";

	public String createWatchlist(JSONObject inputJsonObj, HttpServletRequest request) {
		Watchlist watchlist = new Watchlist();
		String name = (String)inputJsonObj.get("name");
		
		String email = (String) request.getSession(false).getAttribute("username");
		String userId = UserDao.getUser(email).get(0).getUserId();
		
		watchlist.setName(name);
		watchlist.setUserId(userId);
		
		factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		EntityManager em = factory.createEntityManager();
		
		
		em.getTransaction().begin();
		em.persist(watchlist);
		em.getTransaction().commit();
		em.close();
		
		return "ok";
	}

	public String addShow(JSONObject inputJsonObj, HttpServletRequest request) {
		ShowsDao showDao = new ShowsDao();
		
		
		String showId = (String)inputJsonObj.get("showId");
		String watchlistName = (String)inputJsonObj.get("watchlistName");
		String email = (String) request.getSession(false).getAttribute("username");
		Customer customer = UserDao.getUser(email).get(0);
		
		//Show show = (Show) showDao.getShowDetails(showId).get(0);
		Watchlist watchlist = getWatchlist(watchlistName, customer.getUserId()).get(0);
		
		//watchlist.addShow(show);
		
		factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		EntityManager em = factory.createEntityManager();
		
		
		em.getTransaction().begin();
		em.merge(watchlist);
		em.getTransaction().commit();
		em.close();
		
		return "ok";
	}
	
	@SuppressWarnings("unchecked")
	public static List<Watchlist> getWatchlist(String watchlistName, String userId) {
		  List<Watchlist> watchlist = null; 
		  factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		  EntityManager em = factory.createEntityManager();
		  Query query = em.createQuery("select t from Watchlist t where t.name = :name and t.userId = :userId");
		  query.setParameter("name", watchlistName);
		  query.setParameter("userId", userId);
		  watchlist = query.getResultList();
	      return  watchlist;
	}

	public Watchlist returnWatchlist(JSONObject inputJsonObj, HttpServletRequest request) {
		
		String watchlistName = (String)inputJsonObj.get("watchlistName");
		String email = (String) request.getSession(false).getAttribute("username");
		Customer customer = UserDao.getUser(email).get(0);
		
		Watchlist watchlist = getWatchlist(watchlistName, customer.getUserId()).get(0);
		
		return watchlist;
	}
	
	
	
}