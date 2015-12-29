package org.recipes.recipeUsesIngredient;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.springframework.stereotype.Service;



@Service
public class RecipeUsesIngredientService {

	
	EntityManagerFactory emf = Persistence.createEntityManagerFactory("recipes");
	
	
	public RecipeUsesIngredient read(Integer id) {	
		EntityManager em = emf.createEntityManager();		
		RecipeUsesIngredient recipeUsesIngredient = em.find(RecipeUsesIngredient.class, id);		
		return recipeUsesIngredient;
	}

}
