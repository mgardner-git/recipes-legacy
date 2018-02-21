package org.recipes.membership;

import org.recipes.groups.Group;
import org.recipes.users.User;
import org.recipes.util.SessionStuff;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Handles requests related to Membership in Groups
 */
@Controller
@RequestMapping(value = "rest/memberships")
public class MembershipController {

	@Autowired
	MembershipService membershipService;
	
	@RequestMapping(value="" , method = RequestMethod.PUT)
	public  @ResponseBody Membership create(@RequestParam int groupId){
		//TODO: validate no duplicates
		User user = SessionStuff.getLoggedInUser();
		Membership join = new Membership();
		Group group = new Group();
		group.setId(groupId);
		join.setGroup(group);
		join.setUser(user);
		Membership result = membershipService.create(join);
		return result;
		
	}
}
