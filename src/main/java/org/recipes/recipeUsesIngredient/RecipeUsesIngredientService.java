package org.recipes.recipeUsesIngredient;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.recipes.ingredients.Ingredient;
import org.recipes.measurementTypes.MeasurementType;
import org.springframework.stereotype.Service;



@Service
public class RecipeUsesIngredientService {

	
	EntityManagerFactory emf = Persistence.createEntityManagerFactory("recipes");
	
	
	public RecipeUsesIngredient read(Integer id) {	
		EntityManager em = emf.createEntityManager();		
		RecipeUsesIngredient recipeUsesIngredient = em.find(RecipeUsesIngredient.class, id);		
		return recipeUsesIngredient;
	}
	
	public RecipeUsesIngredient create(RecipeUsesIngredient rui){
		if (rui.getIngredient() == null || rui.getIngredient().getId() == null){
			throw new IllegalArgumentException("Each row in the ingredient list of a recipe must have an active ingredient");
		}
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		
		em.persist(rui);
		em.flush();
		em.getTransaction().commit();		
		return rui;
	}

}
