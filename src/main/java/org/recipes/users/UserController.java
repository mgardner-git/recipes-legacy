package org.recipes.users;

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
@RequestMapping(value = "rest/users")
public class UserController {
	
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	@Autowired
	UserService userService;

	@RequestMapping(value="/" , method = RequestMethod.POST)
	public  @ResponseBody User create(@RequestBody User user){
		User result = userService.create(user);
		return result;
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public  @ResponseBody User read(@PathVariable String id){
		User result = userService.read(id);
		return result;
	}
	
	@RequestMapping(value="/", method=RequestMethod.PUT)
	public  @ResponseBody User update(User user){
		User result = userService.update(user);
		return result;
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
	public void delete(@PathVariable Integer id){
		userService.delete(id);
	}
	
}
