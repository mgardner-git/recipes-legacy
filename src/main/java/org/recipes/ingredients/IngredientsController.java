package org.recipes.ingredients;

import java.util.List;

import org.recipes.users.User;
import org.recipes.util.AutoCompleteDTO;
import org.recipes.util.SessionStuff;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Handles requests related to ingredients.
 */
@Controller
@RequestMapping(value = "rest/ingredients")
public class IngredientsController {
	
	private static final Logger logger = LoggerFactory.getLogger(IngredientsController.class);
	@Autowired
	IngredientService ingredientService;

	
	@RequestMapping(value="" , method = RequestMethod.POST)
	public  @ResponseBody Ingredient create(@RequestBody Ingredient ingredient){
		Ingredient result = ingredientService.create(ingredient);
		return result;
	}
	
	@RequestMapping(value="", method=RequestMethod.GET)
	public @ResponseBody List<AutoCompleteDTO> search(@RequestParam(value="term") String term){			
		List<Ingredient> results = ingredientService.search(term);	
		List<AutoCompleteDTO> autoCompleteResults = AutoCompleteDTO.transform(results);
		return autoCompleteResults;
	}
	
	
	@RequestMapping(value="myIngredients", method=RequestMethod.GET)
	public @ResponseBody List<AutoCompleteDTO> getMyIngredients(){
		User user = SessionStuff.getLoggedInUser();
		List<Ingredient> results = ingredientService.getMyIngredients(user);
		List<AutoCompleteDTO> transformedResults = AutoCompleteDTO.transform(results);
		return transformedResults;
	}
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public  @ResponseBody Ingredient read(@PathVariable Integer id){
		Ingredient result = ingredientService.read(id);
		return result;
	}
	
	@RequestMapping(value="", method=RequestMethod.PUT)
	public  @ResponseBody Ingredient update(Ingredient ingredient){
		Ingredient result = ingredientService.update(ingredient);
		return result;
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
	public void delete(@PathVariable Integer id){
		ingredientService.delete(id);
	}
	
}
