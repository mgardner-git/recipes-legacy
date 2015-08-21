package org.recipes.services;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.recipes.dto.*;
import org.recipes.model.*;
import org.recipes.dto.RecipeDTO;
import org.springframework.stereotype.Service;

@Service
public class RecipesService {

	EntityManagerFactory emf = Persistence.createEntityManagerFactory("Recipes");
	
	public RecipeDTO getRecipe(Integer id) {
		EntityManager em = emf.createEntityManager();
		Recipe recipe = em.find(Recipe.class,id);
		return new RecipeDTO(recipe,true);
	}
	public List<RecipeDTO> getMyRecipes(UserDTO user){
		
		EntityManager em = emf.createEntityManager();
		TypedQuery<Recipe> query = em.createNamedQuery("Recipe.myRecipes", Recipe.class);			
		User entityUser = user.constructUserEntity();
		query.setParameter("owner", entityUser);
		List<Recipe> myRecipes = query.getResultList();
		List<RecipeDTO> results = new ArrayList<RecipeDTO>();
		for (Recipe recipe : myRecipes){
			recipe.getRecipeUsesIngredients(); //??
			results.add(new RecipeDTO(recipe,true));
		}
		return results;
	}
	
	public void deleteRecipe(UserDTO user, Integer recipeId) {
		//first verify that the user owns the recipe		
		EntityManager em = emf.createEntityManager();						 
		CriteriaBuilder builder = em.getCriteriaBuilder();		
		Recipe recipeEntity = em.find(Recipe.class, recipeId);
		if (!recipeEntity.getUser().getId().equals(user.getId())){		
			throw new IllegalArgumentException ("User " + user.getId() + " does not own recipe " + recipeEntity.getTitle());
	  	}else {
			em.getTransaction().begin();		
			em.remove(recipeEntity);
			em.getTransaction().commit();	
	  	}
	}
	
	public RecipeDTO createOrModifyRecipe(UserDTO user, RecipeDTO recipeForm) {
		EntityManager em = emf.createEntityManager();
		if (recipeForm.getId() != null) {
			//first verify that the user owns the recipe
			Recipe recipeEntity = em.find(Recipe.class, recipeForm.getId());			
		  	if (!recipeEntity.getUser().getId().equals(user.getId())) {
		  		throw new IllegalArgumentException ("User " + user.getId() + " does not own the recipe " + recipeForm.getTitle());
		  	}
		}
		recipeForm.setUser(user);
	  	Recipe RecipeEntity = recipeForm.constructEntity();
		em.getTransaction().begin();		
		RecipeEntity = em.merge(RecipeEntity);
		em.persist(RecipeEntity);		 
		em.getTransaction().commit();	
	  	RecipeDTO result = new RecipeDTO(RecipeEntity);
	  	return result;
	  			
	}
}
