package org.recipes.recipes;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.recipes.ingredients.Ingredient;
import org.recipes.ingredients.IngredientService;
import org.recipes.measurementTypes.MeasurementType;
import org.recipes.measurementTypes.MeasurementTypeService;
import org.recipes.recipeUsesIngredient.RecipeUsesIngredient;
import org.recipes.users.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class RecipeService {

	
	EntityManagerFactory emf = Persistence.createEntityManagerFactory("recipes");
	@Autowired
	IngredientService ingredientService;
	@Autowired
	MeasurementTypeService measurementTypeService;
	
	
	public List<Recipe> getMyRecipes(User user){
		EntityManager em = emf.createEntityManager();
		Query query = em.createNamedQuery("Recipe.myRecipes");
		query.setParameter("user",  user);
		@SuppressWarnings("unchecked")
		List<Recipe> results = query.getResultList();
		return results;
	}
	
	public Recipe create(Recipe recipe) {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		for (RecipeUsesIngredient rui : recipe.getRecipeUsesIngredients()){
			rui.setRecipe(recipe); //ensures referential integrity, ensures that the table constraints will be met
			Ingredient persistedEntity = ingredientService.createOrUpdate(em,rui.getIngredient());
			rui.setIngredient(persistedEntity);
			
			MeasurementType persistedMt = measurementTypeService.createOrUpdate(em,rui.getMeasurementType());
			rui.setMeasurementType(persistedMt);
		}
		
		em.persist(recipe);
		em.flush();
		em.getTransaction().commit();		
		return recipe;
	}
	
	public Recipe read(Integer id) {	
		EntityManager em = emf.createEntityManager();		
		Recipe recipe = em.find(Recipe.class, id);		
		return recipe;
	}

	
	private List<RecipeUsesIngredient> identifyMissingChildren(EntityManager em, Recipe recipe){
		Recipe base = em.find(Recipe.class, recipe.getId());
		List<RecipeUsesIngredient> missingChildren = new ArrayList<RecipeUsesIngredient>(); //these are the rui's that have been deleted on the edit page.
		if (base == null) {
			return new ArrayList<RecipeUsesIngredient>();
		}else{
			for (RecipeUsesIngredient previouslyExistingRui : base.getRecipeUsesIngredients()){
				boolean found = false;
				for (RecipeUsesIngredient ruiAfterEditing : recipe.getRecipeUsesIngredients()){
					if (ruiAfterEditing.getId().equals(previouslyExistingRui)){
						found = true;
					}
				}
				if (!found){
					missingChildren.add(previouslyExistingRui); //need to delete this one					
				}
			}
		}
		return missingChildren;
	}
	
	public Recipe update(Recipe recipe) {
		EntityManager em = emf.createEntityManager();
		Recipe base = em.find(Recipe.class, recipe.getId());
		if (base == null) {
			//TODO: 404
			throw new IllegalArgumentException("Can't find recipe with id " + recipe.getId());
		}else {			
			em.getTransaction().begin();
			
			List<RecipeUsesIngredient> ruis = new ArrayList<RecipeUsesIngredient>();
			for (RecipeUsesIngredient rui : recipe.getRecipeUsesIngredients()){
				rui.setRecipe(recipe); //ensures referential integrity, ensures that the table constraints will be met
				Ingredient persistedEntity = ingredientService.createOrUpdate(em,rui.getIngredient()); //?? transaction issues ??
				rui.setIngredient(persistedEntity);
				
				MeasurementType persistedMt = measurementTypeService.createOrUpdate(em,rui.getMeasurementType());
				rui.setMeasurementType(persistedMt);
				ruis.add(rui);
				em.persist(rui);
			}
			List<RecipeUsesIngredient> missingChildren = identifyMissingChildren(em,recipe);
			for (RecipeUsesIngredient deleteMe : missingChildren){
				deleteMe.setRecipe(base);
				em.persist(deleteMe);
				em.remove(deleteMe);
			}
			
			
			base.setInstructions(recipe.getInstructions());
			base.setTitle(recipe.getTitle());
			base.setRecipeUsesIngredients(ruis);
			
			
			em.persist(base);			
			em.flush();
			Recipe result = em.find(Recipe.class, base.getId()); 
			em.getTransaction().commit();
			return result;
		}
	}
	
	public boolean delete(Integer id) {
		EntityManager em = emf.createEntityManager();
		Recipe recipe = em.find(Recipe.class, id);
		if (recipe == null) {
			throw new IllegalArgumentException("Can't find recipe with id " + id);			
		}else {
			em.getTransaction().begin();
			em.remove(recipe);
			em.flush();
			em.getTransaction().commit();
		}
		return true;
	}
}
