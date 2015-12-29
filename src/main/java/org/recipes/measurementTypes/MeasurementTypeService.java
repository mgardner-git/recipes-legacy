package org.recipes.measurementTypes;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.recipes.ingredients.Ingredient;
import org.springframework.stereotype.Service;



@Service
public class MeasurementTypeService {

	
	EntityManagerFactory emf = Persistence.createEntityManagerFactory("recipes");
		
	
	public MeasurementType createOrUpdate(EntityManager em, MeasurementType mt){
		if (mt.getId() != null){
			return update(em,mt);
		}else{
			List<MeasurementType> matches = searchExact(mt.getTitle());
			if (matches.size() > 0){
				//The user is trying to create a new ingredient, but one with a matching title already exists
				return matches.get(0);
			}else{
				return create(em,mt);
			}
		}
	}
	
	/**
	 * Returns all MeasurementTypes whose title exactly matches the given term as a prefix, ignoring case and leading or trailing whitespace
	 */
	public List<MeasurementType> searchExact(String term){
		EntityManager em = emf.createEntityManager();
		Query query = em.createNamedQuery("MeasurementType.searchExact");
		query.setParameter("term", term);
		@SuppressWarnings("unchecked")
		List<MeasurementType> results = query.getResultList();
		return results;		
	}
	
	public MeasurementType create(MeasurementType measurementType) {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		MeasurementType result =  create(em, measurementType);
		em.getTransaction().commit();
		return result;
	}
	
	public MeasurementType create(EntityManager em, MeasurementType measurementType) {		
		
		em.persist(measurementType);
		em.flush();
				
		return measurementType;
	}
	
	public List<MeasurementType> readAll(){
		EntityManager em = emf.createEntityManager();
		Query query = em.createNamedQuery("MeasurementType.findAll");
		@SuppressWarnings("unchecked")
		List<MeasurementType> results = query.getResultList();
		return results;
	}
	public MeasurementType read(Integer id) {	
		EntityManager em = emf.createEntityManager();		
		MeasurementType measurementType = em.find(MeasurementType.class, id);		
		return measurementType;
	}

	public MeasurementType update(MeasurementType measurementType) {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		MeasurementType result = update(em, measurementType);
		em.getTransaction().commit();
		return result;
	}
	public MeasurementType update(EntityManager em, MeasurementType measurementType) {		
		MeasurementType base = em.find(MeasurementType.class, measurementType.getId());
		if (base == null) {
			//TODO: 404
			throw new IllegalArgumentException("Can't find measurementType with id " + measurementType.getId());
		}else {			
			base.setDescription(measurementType.getDescription());
			base.setTitle(measurementType.getTitle());
			em.persist(base);
			em.flush();
			MeasurementType result = em.find(MeasurementType.class, base.getId());			
			return result;
		}
	}
	
	public boolean delete(Integer id) {
		EntityManager em = emf.createEntityManager();
		MeasurementType measurementType = em.find(MeasurementType.class, id);
		if (measurementType == null) {
			throw new IllegalArgumentException("Can't find measurementType with id " + id);			
		}else {
			em.getTransaction().begin();
			em.remove(measurementType);
			em.flush();
			em.getTransaction().commit();
		}
		return true;
	}
}
