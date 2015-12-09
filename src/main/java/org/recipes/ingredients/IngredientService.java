package org.recipes.ingredients;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import org.springframework.stereotype.Service;



@Service
public class IngredientService {

	
	EntityManagerFactory emf = Persistence.createEntityManagerFactory("recipes");	
	
	public Ingredient create(Ingredient ingredient) {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		em.persist(ingredient);
		em.flush();
		em.getTransaction().commit();		
		return ingredient;
	}
	
	public List<Ingredient> search(String term){
		EntityManager em = emf.createEntityManager();
		Query query = em.createNamedQuery("Ingredient.search");
		query.setParameter("term", term + '%');
		@SuppressWarnings("unchecked")
		List<Ingredient> results = query.getResultList();
		return results;
	}
	
	public Ingredient read(Integer id) {	
		EntityManager em = emf.createEntityManager();		
		Ingredient ingredient = em.find(Ingredient.class, id);		
		return ingredient;
	}

	public Ingredient update(Ingredient ingredient) {
		EntityManager em = emf.createEntityManager();
		Ingredient base = em.find(Ingredient.class, ingredient.getId());
		if (base == null) {
			//TODO: 404
			throw new IllegalArgumentException("Can't find ingredient with id " + ingredient.getId());
		}else {
			
			em.getTransaction().begin();
			base.setDescription(ingredient.getDescription());
			base.setTitle(ingredient.getTitle());
			em.persist(base);
			em.flush();
			Ingredient result = em.find(Ingredient.class, base.getId()); 
			em.getTransaction().commit();
			return result;
		}
	}
	
	public boolean delete(Integer id) {
		EntityManager em = emf.createEntityManager();
		Ingredient ingredient = em.find(Ingredient.class, id);
		if (ingredient == null) {
			throw new IllegalArgumentException("Can't find ingredient with id " + id);			
		}else {
			em.getTransaction().begin();
			em.remove(ingredient);
			em.flush();
			em.getTransaction().commit();
		}
		return true;
	}
}
