package org.recipes.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

import org.recipes.autocomplete.AutoComplete;
import org.recipes.dto.IngredientDTO;
import org.recipes.dto.RecipeUsesIngredientDTO;
import org.recipes.dto.UserDTO;
import org.recipes.model.Ingredient;
import org.recipes.model.Recipe;
import org.recipes.model.RecipeUsesIngredient;
import org.recipes.model.User;
import org.springframework.stereotype.Service;


@Service
public class IngredientsService {

	EntityManagerFactory emf = Persistence.createEntityManagerFactory("Recipes");
	
	public List<AutoComplete> getIngredients(String term){
		EntityManager em = emf.createEntityManager();	
		TypedQuery<Ingredient> query = em.createNamedQuery("Ingredient.findAll", Ingredient.class);
		query.setParameter("term", "%" + term + "%");
		List<Ingredient> ingredientEntities = query.getResultList();
		List<AutoComplete> results = new ArrayList<AutoComplete>();
		for (Ingredient ingredientEntity : ingredientEntities){
			results.add(new IngredientDTO(ingredientEntity));
		}
		return results;
	}
	
	public List<IngredientDTO> getMyIngredients(UserDTO user){
		
		EntityManager em = emf.createEntityManager();						 
		CriteriaBuilder builder = em.getCriteriaBuilder();		
		CriteriaQuery<Ingredient> cq = builder.createQuery(Ingredient.class);		
	    Root<Ingredient> root = cq.from(Ingredient.class);	    
	  	cq.select(root);	
	  	
	  	Join<Ingredient, RecipeUsesIngredient> rui = root.join("recipeUsesIngredients");
	  	Join<RecipeUsesIngredient, Recipe> r = rui.join("recipe");
	  	Join<Recipe, User> u = r.join("user");
	  	
	  	Path userPath = u.get("id");
	  	Predicate userPred = builder.equal(userPath, user.getId()); //only interested in ingredients in recipes belonging to this user
	  	cq.where(userPred);
	  	TypedQuery<Ingredient> query = em.createQuery(cq);
	  	List<Ingredient> ingredients = query.getResultList();
	  	List<IngredientDTO> results = new ArrayList<IngredientDTO>(ingredients.size());
	  	for (Ingredient entity : ingredients) {
	  		IngredientDTO ingredientForm = new IngredientDTO(entity);
	  		for (RecipeUsesIngredient ruiEntity : entity.getRecipeUsesIngredients()) {
	  			RecipeUsesIngredientDTO ruiForm = new RecipeUsesIngredientDTO(ruiEntity,true);
	  			ruiForm.setIngredient(null); //avoid duplication of data
	  			ingredientForm.getRecipeUsesIngredients().add(ruiForm);	  			
	  		}	  		
	  		results.add(ingredientForm);
	  	}
	  	return results;
	}
	
	public IngredientDTO createOrModifyIngredient(UserDTO user, IngredientDTO ingredientForm) {
		EntityManager em = emf.createEntityManager();
		if (ingredientForm.getId() != null) {
			//first verify that the user owns a recipe with the given ingredient
			
									 
			CriteriaBuilder builder = em.getCriteriaBuilder();		
			CriteriaQuery<Ingredient> cq = builder.createQuery(Ingredient.class);		
		    Root<Ingredient> root = cq.from(Ingredient.class);	    
		  	cq.select(root);	
		  	
		  	Join<Ingredient, RecipeUsesIngredient> rui = root.join("recipeUsesIngredients");
		  	Join<RecipeUsesIngredient, Recipe> r = rui.join("recipe");
		  	Join<Recipe, User> u = r.join("user");
		  	
		  	Path<String> userPath = u.get("id");
		  	Predicate userPred = builder.equal(userPath, user.getId()); //only interested in ingredients in recipes belonging to this user
		  	Path<Integer> ingredientPath = root.get("id");
		  	Predicate ingredientPred = builder.equal(ingredientPath, ingredientForm.getId());
		  	Predicate compoundPredicate = builder.and(userPred,ingredientPred);
		  	cq.where(compoundPredicate);
		  	
		  	TypedQuery<Ingredient> verifyQuery = em.createQuery(cq);
		  	List<Ingredient> verify = verifyQuery.getResultList();
		  	if (verify.size() == 0) {
		  		throw new IllegalArgumentException ("User " + user.getId() + " does not own a recipe which uses " + ingredientForm.getTitle());
		  	}
		}
		
	  	Ingredient ingredientEntity = ingredientForm.constructEntity();
		em.getTransaction().begin();		
		ingredientEntity = em.merge(ingredientEntity);
		em.persist(ingredientEntity);		 
		em.getTransaction().commit();	
	  	IngredientDTO result = new IngredientDTO(ingredientEntity);
	  	return result;
	  			
	}
}
