package org.recipes.controllers;

import org.recipes.dto.GroupDTO;
import org.recipes.dto.MembershipDTO;
import org.recipes.dto.UserDTO;
import org.recipes.services.MembershipsService;
import org.recipes.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value="rest/membership")
public class MembershipController {

	@Autowired
	MembershipsService membershipsService;
	@Autowired
	UsersService usersService;
	
	
	@RequestMapping(value="{id}", method=RequestMethod.GET)
	public @ResponseBody MembershipDTO getMembership(@PathVariable Integer id) {
		MembershipDTO result = membershipsService.getMembership(id);
		return result;
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
	public void deleteMembership(@PathVariable Integer id) {
		UserDTO loggedInUser = usersService.getLoggedInUser();
		membershipsService.deleteMembership(loggedInUser, id);
	}
	
	@RequestMapping(value="" , method=RequestMethod.PUT)	
	public @ResponseBody MembershipDTO modifyMembership(@RequestBody MembershipDTO MembershipForm) {
		UserDTO loggedInUser = usersService.getLoggedInUser();
		return membershipsService.createOrModifyMembership(loggedInUser, MembershipForm);
	}
	
	@RequestMapping(value="{groupId}", method=RequestMethod.POST)
	public @ResponseBody MembershipDTO createMembership(@PathVariable Integer groupId) {
		UserDTO loggedInUser = usersService.getLoggedInUser();
		MembershipDTO membershipForm = new MembershipDTO();
		membershipForm.setUser(loggedInUser);
		GroupDTO group = new GroupDTO();
		group.setId(groupId);
		membershipForm.setGroup(group);		
		return membershipsService.createOrModifyMembership(loggedInUser, membershipForm);		
	}
	
}
