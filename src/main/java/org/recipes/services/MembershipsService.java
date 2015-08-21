package org.recipes.services;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;

import org.recipes.dto.MembershipDTO;
import org.recipes.dto.UserDTO;
import org.recipes.model.Membership;
import org.springframework.stereotype.Service;

@Service
public class MembershipsService {

	EntityManagerFactory emf = Persistence.createEntityManagerFactory("Recipes");
	
	public MembershipDTO getMembership(Integer id) {
		EntityManager em = emf.createEntityManager();
		Membership member = em.find(Membership.class,id);
		return new MembershipDTO(member);
	}
	
	public void deleteMembership(UserDTO user, Integer membershipId) {				
		EntityManager em = emf.createEntityManager();
		CriteriaBuilder builder = em.getCriteriaBuilder();		
		Membership membershipEntity = em.find(Membership.class, membershipId);
		if (membershipEntity == null || !membershipEntity.getUser().getId().equals(user.getId())){		
			throw new IllegalArgumentException ("That membership record, id# " + membershipId + " does not exist.");
	  	}else {
			em.getTransaction().begin();		
			em.remove(membershipEntity);
			em.getTransaction().commit();	
	  	}
	}
	
	public MembershipDTO createOrModifyMembership(UserDTO user, MembershipDTO membershipForm) {
		EntityManager em = emf.createEntityManager();
		
		if (membershipForm.getId() == null) {
			//verify that we're not double booking
			TypedQuery<Membership> query = em.createNamedQuery("Membership.verifyMembership", Membership.class);
			query.setParameter("userId", user.getId());
			query.setParameter("groupId", membershipForm.getId());
			List<Membership> verify = query.getResultList();
			if (verify.size() > 0) {
				throw new IllegalArgumentException("User " + user.getId() + " is already a member of that group.");
			}
			membershipForm.setJoinDate(new java.util.Date());
		}	
		else {
			Membership membershipEntity = em.find(Membership.class, membershipForm.getId());			
		  	if (membershipEntity == null || !membershipEntity.getUser().getId().equals(user.getId())) {
		  		throw new IllegalArgumentException ("User " + user.getId() + " does not own that membership record.");
		  	}
		}
		membershipForm.setUser(user);
	  	Membership MembershipEntity = membershipForm.constructEntity();
		em.getTransaction().begin();		
		MembershipEntity = em.merge(MembershipEntity);
		em.persist(MembershipEntity);		 
		em.getTransaction().commit();	
	  	MembershipDTO result = new MembershipDTO(MembershipEntity);
	  	return result;
	  			
	}
}
