package org.recipes.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.recipes.dto.RecipeDTO;
import org.recipes.dto.UserDTO;
import org.recipes.services.RecipesService;
import org.recipes.web.SessionConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Controller
@RequestMapping(value="rest/recipes")
public class RecipesController {

	@Autowired
	RecipesService recipesService;
	
	@RequestMapping(value="" , method=RequestMethod.GET)
	public @ResponseBody List<RecipeDTO> getMyRecipes(){
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		HttpServletRequest request = attr.getRequest();
		HttpSession session =  request.getSession(true); // true == allow create
		UserDTO loggedInUser = (UserDTO)session.getAttribute(SessionConstants.USER);
		if (loggedInUser != null) {
			List<RecipeDTO> results = recipesService.getMyRecipes(loggedInUser);
			return results;
		}else {
			return null; //??
		}
	}
	
	@RequestMapping(value="{id}", method=RequestMethod.GET)
	public @ResponseBody RecipeDTO getRecipe(@PathVariable Integer id) {
		RecipeDTO result = recipesService.getRecipe(id);
		return result;
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
	public @ResponseBody boolean deleteRecipe(@PathVariable Integer id) {
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		HttpServletRequest request = attr.getRequest();
		HttpSession session =  request.getSession(true); // true == allow create
		UserDTO loggedInUser = (UserDTO)session.getAttribute(SessionConstants.USER);
		if (loggedInUser != null) {
			recipesService.deleteRecipe(loggedInUser, id);
			return true;
		}else {
			return false;
		}
	}
	
	@RequestMapping(value="" , method=RequestMethod.PUT)	
	public @ResponseBody RecipeDTO modifyRecipe(@RequestBody RecipeDTO recipeForm) {
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		HttpServletRequest request = attr.getRequest();
		HttpSession session =  request.getSession(true); // true == allow create
		UserDTO loggedInUser = (UserDTO)session.getAttribute(SessionConstants.USER);
		if (loggedInUser != null) {
			return recipesService.createOrModifyRecipe(loggedInUser, recipeForm);
			
		}else {
			return null;
		}		
	}
	
	@RequestMapping(value="", method=RequestMethod.POST)
	public @ResponseBody RecipeDTO createRecipe(@RequestBody RecipeDTO recipeForm) {
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		HttpServletRequest request = attr.getRequest();
		HttpSession session = request.getSession(true);
		UserDTO loggedInUser = (UserDTO)session.getAttribute(SessionConstants.USER);
		if (loggedInUser != null) {
			return recipesService.createOrModifyRecipe(loggedInUser, recipeForm);
		}else {
			return null;
		}
	}
	
}
