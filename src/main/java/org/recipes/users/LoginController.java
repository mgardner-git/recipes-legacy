package org.recipes.users;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.recipes.util.SessionStuff;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * Handles requests for the application home page.
 */
@Controller
public class LoginController {
	
	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
	@Autowired
	UserService userService;
	
	@RequestMapping(value="/doLogin")
	public String doLogin(@RequestParam String username, @RequestParam String password){
		User loggedInUser = userService.login(username, password);
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		HttpServletRequest request = attr.getRequest();
		HttpSession session =  request.getSession(true); // true == allow create

		if (loggedInUser == null){
			request.setAttribute(SessionStuff.LOGIN_ERROR, "We could not find a user matching that username and password.");
			logger.info("Could not authenticate user " + username);
			return "forward:/login.jsp"; //forward back to the login form			
		}else{
			logger.info("Authenticated user " + username);
			session.setAttribute(SessionStuff.USER, loggedInUser);
			return "forward:/myRecipes.jsp";
		}
	}

	@RequestMapping(value="/logout", method=RequestMethod.GET)
	public String doLogout(){
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		HttpServletRequest request = attr.getRequest();
		HttpSession session =  request.getSession(true); // true == allow create
		session.setAttribute(SessionStuff.USER, null);
		request.setAttribute(SessionStuff.LOGIN_ERROR, "You have been logged out");
		return "login";
	}
	
}
