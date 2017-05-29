package org.recipes.groups;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.recipes.ingredients.Ingredient;
import org.recipes.users.User;
import org.springframework.stereotype.Service;



@Service
public class GroupService {

	
	EntityManagerFactory emf = Persistence.createEntityManagerFactory("recipes");	
	
	
	
	public Group create(Group group){
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		em.persist(group);
		em.flush();				
		em.getTransaction().commit();
		return group;
	}
	
	
	
	/**
	 * Returns all groups which are attached to the given user
	 */
	public List<Group> getMyGroups(User user){
		EntityManager em = emf.createEntityManager();
		Query query = em.createNamedQuery("Group.myGroups");
		query.setParameter("user", user);
		@SuppressWarnings("unchecked")
		List<Group> results = query.getResultList();
		return results;			
	}
	
	/**
	 * Returns all groups whose title has the given term as a prefix
	 * @param term
	 * @return
	 */
	public List<Group> search(String term){
		EntityManager em = emf.createEntityManager();
		Query query = em.createNamedQuery("Group.search");
		query.setParameter("term", term + '%');
		@SuppressWarnings("unchecked")
		List<Group> results = query.getResultList();
		return results;
	}
	
	public Group read(Integer id) {	
		EntityManager em = emf.createEntityManager();		
		Group group = em.find(Group.class, id);		
		return group;
	}

	public Group update(Group group){
		EntityManager em = emf.createEntityManager();
		
		Group base = em.find(Group.class, group.getId());
		if (base == null) {
			//TODO: 404
			throw new IllegalArgumentException("Can't find group with id " + group.getId());
		}else {		
			em.getTransaction().begin();
			base.setDescription(group.getDescription());
			base.setTitle(group.getTitle());
			em.persist(base);
			em.flush();
			Group result = em.find(Group.class, base.getId());			
		
			em.getTransaction().commit();
			return result;
		}
		
	}
	
	public boolean delete(Integer id) {
		EntityManager em = emf.createEntityManager();
		Group group = em.find(Group.class, id);
		if (group == null) {
			throw new IllegalArgumentException("Can't find group with id " + id);			
		}else {
			em.getTransaction().begin();
			em.remove(group);
			em.flush();
			em.getTransaction().commit();
		}
		return true;
	}
}
