package org.recipes.servlets;

import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.recipes.dto.UserDTO;
import org.recipes.model.User;
import org.recipes.web.SessionConstants;


public class LoginServlet extends HttpServlet {
	private EntityManagerFactory emf;
	
	
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    public void init(){
    	emf = Persistence.createEntityManagerFactory("Recipes");
    }
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		UserDTO result = login(username,password);
		
		if (result == null){
			request.setAttribute(SessionConstants.LOGIN_ERROR, "User not found");
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/login.jsp");
			dispatcher.forward(request,response);
		}else{
			request.getSession().setAttribute(SessionConstants.USER, result);
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/myRecipes.jsp");
			dispatcher.forward(request,response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	public UserDTO login(String username, String password){		
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
