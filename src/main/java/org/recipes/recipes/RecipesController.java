package org.recipes.recipes;

import java.util.List;

import org.recipes.users.User;
import org.recipes.util.SessionStuff;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Handles requests for the application home page.
 */
@Controller
@RequestMapping(value = "rest/recipes")
public class RecipesController {
	
	private static final Logger logger = LoggerFactory.getLogger(RecipesController.class);
	@Autowired
	RecipeService recipeService;

	@RequestMapping(value="myRecipes", method=RequestMethod.GET)
	public @ResponseBody List<Recipe> getMyRecipes(){
		User user = SessionStuff.getLoggedInUser();
		return recipeService.getMyRecipes(user);
	}
	
	@RequestMapping(value="" , method = RequestMethod.POST)
	public  @ResponseBody Recipe create(@RequestBody Recipe recipe){
		User user = SessionStuff.getLoggedInUser();
		recipe.setUser(user);
		Recipe result = recipeService.create(recipe);
		return result;
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public  @ResponseBody Recipe read(@PathVariable Integer id){
		Recipe result = recipeService.read(id);
		return result;
	}
	
	@RequestMapping(value="", method=RequestMethod.PUT)
	public  @ResponseBody Recipe update(@RequestBody Recipe recipe){
		User user = SessionStuff.getLoggedInUser();
		recipe.setUser(user);				
		Recipe result = recipeService.update(recipe);
		return result;
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
	public @ResponseBody boolean delete(@PathVariable Integer id){
		recipeService.delete(id);
		return true;
	}
	
}
