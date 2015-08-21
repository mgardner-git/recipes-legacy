package org.recipes.controllers;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.recipes.dto.UserDTO;
import org.recipes.model.User;
import org.recipes.web.SessionConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Controller
public class LoginController {


	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String performLogin(@RequestParam("username") String username, @RequestParam("password") String password) {
		UserDTO result = login(username,password);
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		HttpServletRequest request = attr.getRequest();
		HttpSession session =  request.getSession(true); // true == allow create
		
		if (result == null){
			request.setAttribute(SessionConstants.LOGIN_ERROR, "User not found");
			return "forward:/login.jsp";
		}else{
			session.setAttribute(SessionConstants.USER, result);
			//RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/myRecipes.jsp");
			//dispatcher.forward(request,response);
			return "forward:/myRecipes.jsp";
		}
	}
	
	public UserDTO login(String username, String password){	
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("Recipes");
		EntityManager em = emf.createEntityManager();
		TypedQuery<User> query = em.createNamedQuery("User.login", User.class);
		query.setParameter("id", username);
		query.setParameter("password",password);
		List<User> results = query.getResultList();
		if (results != null && results.size() == 1){
			User entity = (User)results.get(0);
			return new UserDTO(entity);
		}else{
			return null;
		}
	}
}
