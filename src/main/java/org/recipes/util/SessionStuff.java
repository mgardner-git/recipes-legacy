package org.recipes.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.recipes.users.User;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;


public class SessionStuff {

	public static final String LOGIN_ERROR = "LOGIN_ERROR";
	public static final String USER = "USER";
	
	public static User getLoggedInUser() {	
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		HttpServletRequest request = attr.getRequest();
		HttpSession session =  request.getSession(true); // true == allow create
		User user = (User)session.getAttribute(USER);
		return user;

	}
	
}

