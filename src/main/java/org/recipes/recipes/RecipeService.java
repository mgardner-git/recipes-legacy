package org.recipes.recipes;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.recipes.users.User;
import org.springframework.stereotype.Service;



@Service
public class RecipeService {

	
	EntityManagerFactory emf = Persistence.createEntityManagerFactory("recipes");
		
	
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

	public Recipe update(Recipe recipe) {
		EntityManager em = emf.createEntityManager();
		Recipe base = em.find(Recipe.class, recipe.getId());
		if (base == null) {
			//TODO: 404
			throw new IllegalArgumentException("Can't find recipe with id " + recipe.getId());
		}else {
			
			em.getTransaction().begin();
			base.setInstructions(recipe.getInstructions());
			base.setTitle(recipe.getTitle());
			base.setRecipeUsesIngredients(recipe.getRecipeUsesIngredients());
			
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
