package org.recipes.users;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.springframework.stereotype.Service;



@Service
public class UserService {

	
	EntityManagerFactory emf = Persistence.createEntityManagerFactory("recipes");
	
		
	
	public User login(String id, String password) {
		EntityManager em = emf.createEntityManager();
		
		//TODO: Get a TypedQuery
		Query query = em.createNamedQuery("User.login");
		query.setParameter("id",id);
		query.setParameter("password",password);
		@SuppressWarnings("unchecked")
		List<User> results = query.getResultList();
		if (results != null && results.size() == 1){
			User entity = results.get(0);
			return entity;
		}else{
			return null;
		}
	}
	
	public User create(User user) {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		em.persist(user);
		em.flush();
		em.getTransaction().commit();		
		return user;
	}
	
	public User read(String id) {	
		EntityManager em = emf.createEntityManager();		
		User user = em.find(User.class, id);		
		return user;
	}

	public User update(User user) {
		EntityManager em = emf.createEntityManager();
		User base = em.find(User.class, user.getId());
		if (base == null) {
			//TODO: 404
			throw new IllegalArgumentException("Can't find user with id " + user.getId());
		}else {
			
			em.getTransaction().begin();
			base.setFirstname(user.getFirstname());
			base.setLastname(user.getLastname());
			base.setPassword(user.getPassword());
			
			
			em.persist(base);
			em.flush();
			User result = em.find(User.class, base.getId()); 
			em.getTransaction().commit();
			return result;
		}
	}
	
	public boolean delete(Integer id) {
		EntityManager em = emf.createEntityManager();
		User user = em.find(User.class, id);
		if (user == null) {
			throw new IllegalArgumentException("Can't find user with id " + id);			
		}else {
			em.getTransaction().begin();
			em.remove(user);
			em.flush();
			em.getTransaction().commit();
		}
		return true;
	}
}
