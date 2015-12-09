package IngredientServiceTest;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.recipes.ingredients.Ingredient;
import org.recipes.ingredients.IngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import junit.framework.TestCase;
@RunWith( SpringJUnit4ClassRunner.class )
@ContextConfiguration(locations={"classpath:root-context.xml"})
public class IngredientServiceTest extends TestCase{

	@Autowired
	IngredientService ingredientService;
	Integer deleteMeId=null;
	@After    
    public void tearDown()
    {
        if (deleteMeId != null) {
            ingredientService.delete(deleteMeId);//make sure our test cases are marked deleted
        }
    }
	@Before
    public  void setup()
    {	
        deleteMeId = null;
    }
    
	@Test
	public void testCreate() {
		Ingredient ingredient = new Ingredient();
		ingredient.setTitle("Test1");
		ingredient.setDescription("desc");
		Ingredient result = ingredientService.create(ingredient);
		assertNotNull(result);
		assertNotNull(result.getId());
		deleteMeId = result.getId();
	}

	
	//this test case is intended to be run after the script in sql.txt
	@Test
	public void testRead() {
		Ingredient result = ingredientService.read(1);
		assertNotNull(result);
		assertNotNull(result.getId());		
	}
	
	//this test case is intended to be run after the script in sql.txt
	@Test
	public void testSearch(){
		List<Ingredient> results = ingredientService.search("zz");
		assertEquals(0,results.size());
		List<Ingredient> results2 = ingredientService.search("chi");
		assertEquals(1, results2.size());
		
	}
	
	@Test
	public void testUpdate() {
		Ingredient ingredient = new Ingredient();
		ingredient.setTitle("Test1");
		ingredient.setDescription("desc");
		Ingredient result = ingredientService.create(ingredient);
		
		assertNotNull(result.getId());
		deleteMeId = result.getId();
		
		result.setTitle("Test2");
		Ingredient updateResult = ingredientService.update(result);
		assertNotNull(updateResult);
		assertEquals(updateResult.getId(), result.getId());
		assertEquals("Test2", updateResult.getTitle());		
		
	}

	@Test	
	public void testDelete() {
		Ingredient ingredient = new Ingredient();
		ingredient.setTitle("Test1");
		ingredient.setDescription("desc");
		Ingredient ingredient2 = ingredientService.create(ingredient);
		boolean result = ingredientService.delete(ingredient2.getId());
		assertTrue(result);
		
		Ingredient result2 = ingredientService.read(ingredient2.getId());
		assertNull(result2);
	}
	
}
