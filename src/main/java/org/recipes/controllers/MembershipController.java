package org.recipes.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.recipes.dto.*;
import org.recipes.services.MembershipsService;
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
@RequestMapping(value="rest/membership")
public class MembershipController {

	@Autowired
	MembershipsService membershipsService;
	
	
	@RequestMapping(value="{id}", method=RequestMethod.GET)
	public @ResponseBody MembershipDTO getMembership(@PathVariable Integer id) {
		MembershipDTO result = membershipsService.getMembership(id);
		return result;
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
	public @ResponseBody boolean deleteMembership(@PathVariable Integer id) {
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		HttpServletRequest request = attr.getRequest();
		HttpSession session =  request.getSession(true); // true == allow create
		UserDTO loggedInUser = (UserDTO)session.getAttribute(SessionConstants.USER);
		if (loggedInUser != null) {
			membershipsService.deleteMembership(loggedInUser, id);
			return true;
		}else {
			return false;
		}
	}
	
	@RequestMapping(value="" , method=RequestMethod.PUT)	
	public @ResponseBody MembershipDTO modifyMembership(@RequestBody MembershipDTO MembershipForm) {
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		HttpServletRequest request = attr.getRequest();
		HttpSession session =  request.getSession(true); // true == allow create
		UserDTO loggedInUser = (UserDTO)session.getAttribute(SessionConstants.USER);
		if (loggedInUser != null) {
			return membershipsService.createOrModifyMembership(loggedInUser, MembershipForm);
			
		}else {
			return null;
		}		
	}
	
	@RequestMapping(value="{groupId}", method=RequestMethod.POST)
	public @ResponseBody MembershipDTO createMembership(@PathVariable Integer groupId) {
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		HttpServletRequest request = attr.getRequest();
		HttpSession session = request.getSession(true);
		UserDTO loggedInUser = (UserDTO)session.getAttribute(SessionConstants.USER);
		if (loggedInUser != null) {
			MembershipDTO membershipForm = new MembershipDTO();
			membershipForm.setUser(loggedInUser);
			GroupDTO group = new GroupDTO();
			group.setId(groupId);
			membershipForm.setGroup(group);
			
			return membershipsService.createOrModifyMembership(loggedInUser, membershipForm);
		}else {
			return null;
		}
	}
	
}
