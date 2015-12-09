package org.recipes.recipes;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.recipes.users.User;
import org.recipes.users.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import junit.framework.TestCase;
@RunWith( SpringJUnit4ClassRunner.class )
@ContextConfiguration(locations={"classpath:root-context.xml"})
public class RecipeServiceTest extends TestCase{

	@Autowired
	RecipeService recipeService;
	@Autowired
	UserService userService;
	Integer deleteMeId=null;
	User user;
	
	@After    
    public void tearDown()
    {
        if (deleteMeId != null) {
            recipeService.delete(deleteMeId);//make sure our test cases are marked deleted
        }
    }
	@Before
    public  void setup()
    {	
		user = userService.read("bob");
        deleteMeId = null;
    }
    
	@Test
	public void testCreate() {
		Recipe recipe = new Recipe();
		recipe.setTitle("Test1");
		recipe.setInstructions("some instructions");
		recipe.setUser(user);
		Recipe result = recipeService.create(recipe);
		assertNotNull(result);
		assertNotNull(result.getId());
		deleteMeId = result.getId();
	}
	
	//this test case is intended to be run after the script in sql.txt
	@Test
	public void testRead() {
		Recipe result = recipeService.read(1);
		assertNotNull(result);
		assertNotNull(result.getId());		
	}
	
	@Test
	public void testUpdate() {
		Recipe recipe = new Recipe();
		recipe.setTitle("Test1");
		recipe.setInstructions("some instructions");
		recipe.setUser(user);
		Recipe result = recipeService.create(recipe);
		
		assertNotNull(result.getId());
		deleteMeId = result.getId();
		
		result.setTitle("Test2");
		Recipe updateResult = recipeService.update(result);
		assertNotNull(updateResult);
		assertEquals(updateResult.getId(), result.getId());
		assertEquals("Test2", updateResult.getTitle());		
		
	}

	@Test	
	public void testDelete() {
		Recipe recipe = new Recipe();
		recipe.setTitle("Test1");
		recipe.setInstructions("some instructions");
		recipe.setUser(user);
		Recipe recipe2 = recipeService.create(recipe);
		
		
		boolean result = recipeService.delete(recipe2.getId());
		assertTrue(result);
		
		Recipe result2 = recipeService.read(recipe2.getId());
		assertNull(result2);
	}
	
}
