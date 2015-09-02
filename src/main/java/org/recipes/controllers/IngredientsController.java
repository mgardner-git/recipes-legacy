package org.recipes.controllers;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.recipes.autocomplete.AJAXUtils;
import org.recipes.autocomplete.AutoComplete;
import org.recipes.dto.IngredientDTO;
import org.recipes.dto.UserDTO;
import org.recipes.model.User;
import org.recipes.services.IngredientsService;
import org.recipes.services.UsersService;
import org.recipes.web.SessionConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Controller
@RequestMapping(value="rest/ingredients")
public class IngredientsController {

	@Autowired
	IngredientsService ingredientsService;
	@Autowired
	UsersService usersService;
	
	@RequestMapping(value="{id}", method=RequestMethod.GET)
	public @ResponseBody IngredientDTO getIngredient(@PathVariable Integer id) {
		return ingredientsService.getIngredient(id);
	}
	
	@RequestMapping(value="myIngredients" , method=RequestMethod.GET)
	public @ResponseBody List<IngredientDTO> getMyIngredients(@RequestParam (required=false) String term){
		UserDTO loggedInUser = usersService.getLoggedInUser();
		List<IngredientDTO> results = ingredientsService.getMyIngredients(loggedInUser, term);
		return results;
	}
	
	@RequestMapping(value="", method=RequestMethod.GET)
	public @ResponseBody List<Map<String,String>> getIngredients(@RequestParam String term){
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		HttpServletRequest request = attr.getRequest();
		HttpSession session =  request.getSession(true); // true == allow create
		UserDTO loggedInUser = (UserDTO)session.getAttribute(SessionConstants.USER);
		if (loggedInUser != null) {
			List<AutoComplete> results = ingredientsService.getIngredients(term);			
			List<Map<String,String>> acResults = AJAXUtils.getAutoCompleteAJAX(results);
			return acResults;
		}else {
			return null; //??
		}
	}
	
	@RequestMapping(value="" , method=RequestMethod.PUT)	
	public @ResponseBody IngredientDTO modifyIngredient(@RequestBody IngredientDTO ingredientForm) {
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		HttpServletRequest request = attr.getRequest();
		HttpSession session =  request.getSession(true); // true == allow create
		UserDTO loggedInUser = (UserDTO)session.getAttribute(SessionConstants.USER);
		if (loggedInUser != null) {
			return ingredientsService.createOrModifyIngredient(loggedInUser, ingredientForm);
			
		}else {
			return null;
		}		
	}
	
	@RequestMapping(value="", method=RequestMethod.POST)
	public @ResponseBody IngredientDTO createIngredient(@RequestBody IngredientDTO ingredientForm) {
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		HttpServletRequest request = attr.getRequest();
		HttpSession session = request.getSession(true);
		UserDTO loggedInUser = (UserDTO)session.getAttribute(SessionConstants.USER);
		if (loggedInUser != null) {
			return ingredientsService.createOrModifyIngredient(loggedInUser, ingredientForm);
		}else {
			return null;
		}
	}
}
