package com.neo.booyah.server.api;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class SessionService {

	public static String createSession(HttpServletRequest request, String username) {
		
		HttpSession session= request.getSession(true);
		session.setMaxInactiveInterval(60);
	    String user = (String) session.getAttribute("user");
	     if (user!=null) {
	    	 
	     } else {
	      user = username;
	      session.setAttribute("sessionId", session.getId());
	      session.setAttribute("username", user);
	     }
	     
	     return session.getId();
		
	}
	
	public static boolean checkSession(HttpServletRequest request, String sessionIdParam) {
		
		HttpSession session= request.getSession(false);
		String sessionId = null;
		if(session != null) {
			sessionId = (String) session.getAttribute("sessionId");
		}
		
		return (session != null) &&  sessionIdParam.equals(sessionId);
		
	}
	
	public static void invalidateSession(HttpServletRequest request) {
		
		HttpSession session= request.getSession(false);
		
		if(session != null) {
			session.invalidate();
		}
		
	}
   
	
}