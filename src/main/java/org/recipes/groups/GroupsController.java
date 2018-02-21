package org.recipes.groups;

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
 * Handles requests related to Groups
 */
@Controller
@RequestMapping(value = "rest/groups")
public class GroupsController {
	
	private static final Logger logger = LoggerFactory.getLogger(GroupsController.class);
	@Autowired
	GroupService service;

	
	@RequestMapping(value="" , method = RequestMethod.POST)
	public  @ResponseBody Group create(@RequestBody Group group){
		Group result = service.create(group);
		return result;
	}
	
	
	@RequestMapping(value="myGroups", method=RequestMethod.GET)
	public @ResponseBody List<Group> getMyGroups(){
		User user = SessionStuff.getLoggedInUser();
		List<Group> groups = service.getMyGroups(user);
		return groups;		
	}
	
	@RequestMapping(value="unjoinedGroups", method=RequestMethod.GET) 
	public @ResponseBody List<Group> getUnjoinedGroups() {
		User user = SessionStuff.getLoggedInUser();
		List<Group> groups = service.getUnjoinedGroups(user);
		return groups;
		
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public  @ResponseBody Group read(@PathVariable Integer id){
		Group result = service.read(id);
		return result;
	}
	
	@RequestMapping(value="", method=RequestMethod.PUT)
	public  @ResponseBody Group update(Group group){
		Group result = service.update(group); //TODO: Check for admin status
		return result;
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
	public void delete(@PathVariable Integer id){
		service.delete(id); //TODO: Check for admin status
	}
	
}
