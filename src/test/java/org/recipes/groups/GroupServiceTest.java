package org.recipes.groups;

import java.util.List;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.recipes.ingredients.Ingredient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import junit.framework.TestCase;

@RunWith( SpringJUnit4ClassRunner.class )
@ContextConfiguration(locations={"classpath:root-context.xml"})

public class GroupServiceTest extends TestCase {
	
	@Autowired GroupService groupService;
	Integer deleteMeId=null;
	
	@After    
    public void tearDown()
    {
        if (deleteMeId != null) {
            groupService.delete(deleteMeId);//make sure our test cases are marked deleted
        }
    }

	@Test
	public void testCreate() {
		Group group = new Group();
		group.setTitle("Test1");
		group.setDescription("desc");
		Group result = groupService.create(group);
		assertNotNull(result);
		assertNotNull(result.getId());
		deleteMeId = result.getId();
	}
	
	//this test case is intended to be run after the script in sql.txt
	@Test
	public void testRead() {
		Group result = groupService.read(1);
		assertNotNull(result);
		assertNotNull(result.getId());		
	}
	
	//this test case is intended to be run after the script in sql.txt
	@Test
	public void testSearch(){
		List<Group> results = groupService.search("zz");
		assertEquals(0,results.size());
		List<Group> results2 = groupService.search("Jap");
		assertEquals(1, results2.size());
		
	}
	
	
	@Test
	public void testUpdate() {
		Group group = new Group();
		group.setTitle("Test1");
		group.setDescription("desc");
		Group result = groupService.create(group);
		
		assertNotNull(result.getId());
		deleteMeId = result.getId();
		
		group.setTitle("Test2");
		Group updateResult = groupService.update(result);
		assertNotNull(updateResult);
		assertEquals(updateResult.getId(), result.getId());
		assertEquals("Test2", updateResult.getTitle());		
		
	}
	
	

	@Test	
	public void testDelete() {
		Group group = new Group();
		group.setTitle("Test1");
		group.setDescription("desc");
		Group group2 = groupService.create(group);
		boolean result = groupService.delete(group2.getId());
		assertTrue(result);
		
		Group result2 = groupService.read(group2.getId());
		assertNull(result2);
	}
}
