package org.recipes.membership;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

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
	
	
}
