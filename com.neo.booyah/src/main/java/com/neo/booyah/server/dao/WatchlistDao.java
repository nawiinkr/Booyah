package com.neo.booyah.server.dao;


import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.json.simple.JSONObject;

import com.neo.booyah.server.entity.Customer;
import com.neo.booyah.server.entity.Show;
import com.neo.booyah.server.entity.Watchlist;
import com.neo.booyah.server.entity.WatchlistDTO;
import com.neo.booyah.server.utils.HibernateUtil;

public class WatchlistDao {
	private static Session session;

	public String createWatchlist(JSONObject inputJsonObj, HttpServletRequest request) {
		Watchlist watchlist = new Watchlist();
		String name = (String)inputJsonObj.get("name");
		
		String email = (String) request.getSession(false).getAttribute("username");
		String userId = UserDao.getUser(email).get(0).getUserId();
		
		watchlist.setName(name);
		watchlist.setUserId(userId);
		
		session = HibernateUtil.getSessionFactory().openSession();
    	session.beginTransaction();
    	
        String res = (String) session.save(watchlist);

        session.getTransaction().commit();
        session.close();
        
		return res;
	}

	public String addShow(JSONObject inputJsonObj, HttpServletRequest request) {
		ShowsDao showDao = new ShowsDao();
		
		
		String showId = (String)inputJsonObj.get("showId");
		String watchlistId = inputJsonObj.get("watchlistId").toString();
		//String email = (String) request.getSession(false).getAttribute("username");
		//Customer customer = UserDao.getUser(email).get(0);
		
		Show show = (Show) showDao.getShowDetails(showId);
		Watchlist watchlist = getWatchlistById(watchlistId);
		
		watchlist.addShow(show);
		
		session = HibernateUtil.getSessionFactory().openSession();
	    session.beginTransaction();
		
		
		session.merge(watchlist);
		session.getTransaction().commit();
		session.close();
		return "ok";
	}
	
	@SuppressWarnings("unchecked")
	public static Watchlist getWatchlistById(String param) {
		  Watchlist watchlist = null;
		  session = HibernateUtil.getSessionFactory().openSession();
	      session.beginTransaction();
	      
	      watchlist = (Watchlist) session.get(Watchlist.class, param);
	      session.getTransaction().commit();
	      session.close();
	      
	      return  watchlist;
	}
	
	@SuppressWarnings("unchecked")
	public static List<WatchlistDTO> getWatchlistsByUser(String user) {
		  List<WatchlistDTO> watchlist = null;
		  session = HibernateUtil.getSessionFactory().openSession();
	      session.beginTransaction();
	      
	      String query = "select * from binged.watchlist where userId= :userId";
	      watchlist = session.createSQLQuery(query).addEntity(WatchlistDTO.class).setParameter("userId", user).list();
	     
	      session.getTransaction().commit();
	      session.close();
	      
	      return  watchlist;
	}

	public Watchlist returnWatchlist(JSONObject inputJsonObj) {
		
		String id = (String)inputJsonObj.get("watchlistId");
		//String email = (String) request.getSession(false).getAttribute("username");
		//Customer customer = UserDao.getUser(email).get(0);
		
		Watchlist watchlist = getWatchlistById(id);
		
		return watchlist;
	}
	
	
	
}