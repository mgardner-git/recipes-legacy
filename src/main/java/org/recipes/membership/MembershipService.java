package org.recipes.membership;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.recipes.groups.Group;
import org.springframework.stereotype.Service;

@Service
public class MembershipService {

	
	EntityManagerFactory emf = Persistence.createEntityManagerFactory("recipes");	
	
	
	
	public Membership create(Membership membership){
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		em.persist(membership);
		em.flush();				
		em.getTransaction().commit();
		return membership;
	}
	
	public boolean delete(String userId, int groupId) {
		
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		
		Query query = em.createNamedQuery("Membership.search");
		query.setParameter("userId", userId);
		query.setParameter("groupId", groupId);
		@SuppressWarnings("unchecked")
		List<Membership> results = query.getResultList();
		if (results.size() == 1) {
			em.remove(results.get(0));
			em.getTransaction().commit();
			return true;
			
		} else {
			
			em.getTransaction().commit();
			throw new RuntimeException("Somehow we've got redundant memberships here");
		}
	
	
	}
	
}
