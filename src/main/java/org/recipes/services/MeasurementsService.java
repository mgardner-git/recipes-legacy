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

import org.recipes.autocomplete.AutoComplete;
import org.recipes.dto.IngredientDTO;
import org.recipes.dto.MeasurementtypeDTO;
import org.recipes.dto.UserDTO;
import org.recipes.model.Ingredient;
import org.recipes.model.Measurementtype;
import org.recipes.model.Recipe;
import org.recipes.model.RecipeUsesIngredient;
import org.recipes.model.User;
import org.springframework.stereotype.Service;


@Service
public class MeasurementsService {

	EntityManagerFactory emf = Persistence.createEntityManagerFactory("Recipes");
	
	public List<AutoComplete> getMeasurements(){
		
		EntityManager em = emf.createEntityManager();	
		TypedQuery<Measurementtype> query = em.createNamedQuery("Measurementtype.findAll", Measurementtype.class);
		List<Measurementtype> mtEntities = query.getResultList();
		List<AutoComplete> mtDtos = new ArrayList<AutoComplete>();
		for (Measurementtype mtEntity : mtEntities){
			mtDtos.add(new MeasurementtypeDTO(mtEntity));
		}
		return mtDtos;
	}
	
	public MeasurementtypeDTO createOrModifyMeasurement(MeasurementtypeDTO measurementForm) {
 		Measurementtype measurementEntity = measurementForm.constructEntity();
 		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();		
		measurementEntity = em.merge(measurementEntity);		
		em.persist(measurementEntity);		 
		em.getTransaction().commit();
		MeasurementtypeDTO result = new MeasurementtypeDTO(measurementEntity);
		return result;
	}
	
	public boolean deleteMeasurement(MeasurementtypeDTO measurementForm) {
		EntityManager em = emf.createEntityManager();
		Measurementtype measurementEntity = em.find(Measurementtype.class, measurementForm.getId());
		if (measurementEntity != null) {
			em.getTransaction().begin();
			em.remove(measurementEntity);
			em.getTransaction().commit();
			return true;
		}else {
			return false;
		}
	}

}
