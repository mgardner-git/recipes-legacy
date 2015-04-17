package recipes;

import java.io.IOException;
import java.util.ArrayList;
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

import model.Recipe;
import model.User;

import org.codehaus.jackson.map.ObjectMapper;

import web.SessionConstants;
import dto.RecipeDTO;
import dto.UserDTO;

/**
 * Servlet implementation class RecipeServlet
 */
public class MyRecipesServlet extends HttpServlet {
	private EntityManagerFactory emf;
	
	
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MyRecipesServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		UserDTO loggedInUser = (UserDTO)request.getSession().getAttribute(SessionConstants.USER);
		if (loggedInUser != null){
			List<RecipeDTO> myRecipes = getMyRecipes(loggedInUser);
			ObjectMapper mapper = new ObjectMapper();
			String myRecipesInJson = mapper.writeValueAsString(myRecipes);
			myRecipesInJson = myRecipesInJson.replace("'", "\\'");			
			request.setAttribute(SessionConstants.MY_RECIPES, myRecipesInJson);
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/myRecipes.jsp");
			dispatcher.forward(request,response);
			
		}else{
			request.setAttribute(SessionConstants.LOGIN_ERROR, "You are not logged in. Please log in.");
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/login.jsp");
			dispatcher.forward(request,response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	private List<RecipeDTO> getMyRecipes(UserDTO user){
		emf = Persistence.createEntityManagerFactory("Recipes");
		EntityManager em = emf.createEntityManager();
		TypedQuery<Recipe> query = em.createNamedQuery("Recipe.myRecipes", Recipe.class);			
		User entityUser = user.constructUserEntity();
		query.setParameter("owner", entityUser);
		List<Recipe> myRecipes = query.getResultList();
		List<RecipeDTO> results = new ArrayList<RecipeDTO>();
		for (Recipe recipe : myRecipes){
			results.add(new RecipeDTO(recipe));
		}
		return results;

	}
}
