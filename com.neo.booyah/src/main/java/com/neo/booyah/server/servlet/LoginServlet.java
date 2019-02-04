package com.neo.booyah.server.servlet;

import javax.servlet.http.HttpServlet;

import com.neo.booyah.server.dao.UserDao;

@SuppressWarnings("serial")
public class LoginServlet extends HttpServlet{
	    public static boolean isValidUser(String username, String password) {
	    	UserDao userDao = new UserDao();
	    	return userDao.authenticateUser(username, password);
	    }
}