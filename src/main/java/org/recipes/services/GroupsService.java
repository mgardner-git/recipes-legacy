package org.recipes.services;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.recipes.dto.GroupDTO;
import org.recipes.dto.GroupsAndRecipesDTO;
import org.recipes.dto.UserDTO;
import org.recipes.model.Group;
import org.recipes.model.User;
import org.springframework.stereotype.Service;




@Service
public class GroupsService {

	EntityManagerFactory emf = Persistence.createEntityManagerFactory("Recipes");
	

	
	public GroupDTO createOrModifyGroup(UserDTO user, GroupDTO groupForm) {
		EntityManager em = emf.createEntityManager();
		if (groupForm.getId() != null) {
			Group groupEntity = em.find(Group.class, groupForm.getId());
					
		  	if (!groupEntity.getAdmin().getId().equals(user.getId())) {
		  		throw new IllegalArgumentException ("User " + user.getId() + " is not the administrator of " + groupForm.getTitle());
		  	}
		}
		
	  	Group groupEntity = groupForm.constructEntity();	  	
		em.getTransaction().begin();		
		groupEntity = em.merge(groupEntity);
		User admin = user.constructUserEntity();
		em.merge(admin);
		groupEntity.setAdmin(admin);
		em.persist(groupEntity);		 
		em.getTransaction().commit();	
	  	GroupDTO result = new GroupDTO(groupEntity);
	  	return result;	  			
	}
	
	public List<GroupsAndRecipesDTO> getGroupsAndRecipes(UserDTO user, Integer recipeId){
		EntityManager em = emf.createEntityManager();
		Query q = em.createNativeQuery(
				"SELECT G.id, G.title, M.id IS NOT NULL AS isMember, T.id from groups G LEFT JOIN " + 
				"(select M.id, M.group_fk from membership M WHERE M.user_fk='" + user.getId()  + "') M ON (G.id=M.group_fk) LEFT JOIN " + 
				"(select T.id, T.group_fk from threads T where T.recipe_fk=" + recipeId + ") T  ON (G.id=T.group_fk);");
		q.setParameter("userId", user.getId());
		q.setParameter("recipeId", recipeId);
		 List dbRows = q.getResultList();
		 
		 List<GroupsAndRecipesDTO> results = new ArrayList<GroupsAndRecipesDTO>();	 
		 for (Object row : dbRows) {
			 GroupDTO groupForm = new GroupDTO();
			 GroupsAndRecipesDTO gr = new GroupsAndRecipesDTO();
			 gr.setGroup(groupForm);
			 Object[] rowData = (Object[])row;
			 groupForm.setId((Integer)rowData[0]);
			 groupForm.setTitle((String)rowData[1]);
			 gr.setMember((Long)rowData[2]>0);			 
			 gr.setThreadId((Integer)rowData[3]);
			 results.add(gr);
		 }
		 return results;	
	}
	
}
