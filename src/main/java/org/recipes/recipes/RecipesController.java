package org.recipes.recipes;

import java.util.List;

import org.recipes.ingredients.Ingredient;
import org.recipes.users.User;
import org.recipes.util.SessionStuff;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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
	
	//jQuery's ajax routine is wierd about doing GET's with an array so I have to do a post here
	@RequestMapping(value="analyzeIngredients", method=RequestMethod.POST)
	public @ResponseBody List<Recipe> analyzeIngredients(@RequestBody List<Ingredient> ingredients, @RequestParam int threshold){
		return recipeService.getRecipesThatCloselyMatchIngredientList(ingredients, threshold);
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
	public  @ResponseBody Recipe update(@RequestBody Recipe recipe) throws IllegalArgumentException{
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
	
	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<Object> exceptionHandler(Exception e){
		String msg = "\"" + e.getMessage() + "\"";
		ResponseEntity<Object> response = new ResponseEntity<Object> (msg,HttpStatus.BAD_REQUEST);
		return response;
	}
	
}
