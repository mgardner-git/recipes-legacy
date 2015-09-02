package org.recipes.controllers;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.recipes.autocomplete.AJAXUtils;
import org.recipes.autocomplete.AutoComplete;
import org.recipes.dto.*;
import org.recipes.services.GroupsService;
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
@RequestMapping(value="rest/groups")
public class GroupsController {

	@Autowired
	GroupsService groupsService;
	@Autowired
	UsersService usersService;
	
	@RequestMapping(value="" , method=RequestMethod.PUT)	
	
	public @ResponseBody GroupDTO modifyGroup(@RequestBody GroupDTO groupForm) {
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		HttpServletRequest request = attr.getRequest();
		HttpSession session =  request.getSession(true); // true == allow create
		UserDTO loggedInUser = (UserDTO)session.getAttribute(SessionConstants.USER);
		if (loggedInUser != null) {
			return groupsService.createOrModifyGroup(loggedInUser, groupForm);
			
		}else {
			return null;
		}		
	}
	
	@RequestMapping(value="", method=RequestMethod.POST)
	public @ResponseBody GroupDTO createGroup(@RequestBody GroupDTO groupForm) {
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		HttpServletRequest request = attr.getRequest();
		HttpSession session = request.getSession(true);
		UserDTO loggedInUser = (UserDTO)session.getAttribute(SessionConstants.USER);
		if (loggedInUser != null) {
			return groupsService.createOrModifyGroup(loggedInUser, groupForm);
		}else {
			return null;
		}
	}
	
	/**
	 * Returns All the groups along with information about whether the given user is a member and whether the given recipe 
	 * has been posted to a thread in that group.
	 */
	@RequestMapping(value="recipesAndGroups/{recipeId}", method=RequestMethod.GET)
	public @ResponseBody List<GroupsAndRecipesDTO> getGroupsAndRecipes(@PathVariable Integer recipeId){
		UserDTO loggedInUser = usersService.getLoggedInUser();
		return groupsService.getGroupsAndRecipes(loggedInUser, recipeId);
	}
	
	@RequestMapping(value="ingredientsAndGroups/{ingredientId}", method=RequestMethod.GET)
	public @ResponseBody List<GroupsAndIngredientsDTO> getGroupsAndIngredients(@PathVariable Integer ingredientId){
		UserDTO loggedInUser = usersService.getLoggedInUser();
		return groupsService.getGroupsAndIngredients(loggedInUser, ingredientId);
	}
}
