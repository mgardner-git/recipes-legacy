package org.recipes.dto;

import java.util.ArrayList;
import java.util.List;

import org.recipes.model.Recipe;
import org.recipes.model.RecipeUsesIngredient;

public class RecipeDTO {

	private Integer id;
	private String instructions;
	private String title;
	private UserDTO user;
	private List<RecipeUsesIngredientDTO> recipeUsesIngredients = new ArrayList<RecipeUsesIngredientDTO>();
	
	public RecipeDTO(){
		
	}
	public RecipeDTO(Recipe entity){
		this(entity,false);
	}
	/** 
	 * @param entity a JPA Recipe entity. This constructor will directly copy the attributes from that entity
	 * @param deep  If true, this constructor will attempt to also construct the RecipeUsesIngredient DTO's from the entity
	 */
	public RecipeDTO(Recipe entity, boolean deep){
		this.recipeUsesIngredients = new ArrayList<RecipeUsesIngredientDTO>();
		if (entity.getRecipeUsesIngredients() != null && deep){
			for (RecipeUsesIngredient ruiEntity : entity.getRecipeUsesIngredients()){
				RecipeUsesIngredientDTO ruiDTO = new RecipeUsesIngredientDTO(ruiEntity,false);
				ruiDTO.setIngredient(new IngredientDTO(ruiEntity.getIngredient()));
				ruiDTO.setMeasurementtype(new MeasurementtypeDTO(ruiEntity.getMeasurementtype()));
				recipeUsesIngredients.add(ruiDTO);
			}
		}
		this.id = entity.getId();
		this.instructions = entity.getInstructions();
		this.title = entity.getTitle();
	}

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	public List<RecipeUsesIngredientDTO> getRecipeUsesIngredients(){return recipeUsesIngredients;}
	public void setRecipeUsesIngredients(List<RecipeUsesIngredientDTO> inRui){this.recipeUsesIngredients = inRui;}
	/**
	 * @return the instructions
	 */
	public String getInstructions() {
		return instructions;
	}

	/**
	 * @param instructions the instructions to set
	 */
	public void setInstructions(String instructions) {
		this.instructions = instructions;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	
	public void setUser(UserDTO user){this.user = user;}
	public UserDTO getUser(){return user;}
	
	
	public Recipe constructEntity(){
		Recipe result = new Recipe();
		result.setId(id);
		result.setTitle(title == null ? "" : title);
		result.setInstructions(instructions == null ? "" : instructions);
		result.setUser(this.user.constructUserEntity());
		

		List<RecipeUsesIngredient> ruiEntities = new ArrayList<RecipeUsesIngredient>();
		for (RecipeUsesIngredientDTO ruiDto : this.recipeUsesIngredients){
			ruiDto.setRecipe(this);
			RecipeUsesIngredient ruiEntity = ruiDto.constructEntity(result);
			ruiEntities.add(ruiEntity);
		}
		result.setRecipeUsesIngredients(ruiEntities);

		return result;		
	}
}
