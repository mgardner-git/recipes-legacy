package org.recipes.dto;

import java.util.ArrayList;
import java.util.List;

import org.recipes.model.Recipe;
import org.recipes.model.User;

public class UserDTO {

	private String id;
	private String firstname;
	private String lastname;
	private String password;
	private List<RecipeDTO> recipes;
	
	public UserDTO() {
		
	}
	public UserDTO(User entity){
		this.id = entity.getId();
		this.firstname = entity.getFirstname();
		this.lastname = entity.getLastname();
		this.password = entity.getPassword();
		
		recipes = new ArrayList<RecipeDTO>();
		if (entity.getRecipes() != null){
			for (Recipe recipe : entity.getRecipes()){
				recipes.add(new RecipeDTO(recipe));
			}
		}
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the firstname
	 */
	public String getFirstname() {
		return firstname;
	}

	/**
	 * @param firstname the firstname to set
	 */
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	/**
	 * @return the lastname
	 */
	public String getLastname() {
		return lastname;
	}

	/**
	 * @param lastname the lastname to set
	 */
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the recipes
	 */
	public List<RecipeDTO> getRecipes() {
		return recipes;
	}

	/**
	 * @param recipes the recipes to set
	 */
	public void setRecipes(List<RecipeDTO> recipes) {
		this.recipes = recipes;
	}
	
	public User constructUserEntity(){
		User entity = new User();
		entity.setId(id);
		entity.setFirstname(firstname);
		entity.setLastname(lastname);
		entity.setPassword(password);
		return entity;
	}
}
