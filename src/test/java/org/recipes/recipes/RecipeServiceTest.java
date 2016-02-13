package org.recipes.recipes;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.recipes.ingredients.Ingredient;
import org.recipes.ingredients.IngredientService;
import org.recipes.measurementTypes.MeasurementType;
import org.recipes.measurementTypes.MeasurementTypeService;
import org.recipes.recipeUsesIngredient.RecipeUsesIngredient;
import org.recipes.recipeUsesIngredient.RecipeUsesIngredientService;
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
	IngredientService ingredientService;
	@Autowired
	MeasurementTypeService measurementTypeService;
	@Autowired
	RecipeUsesIngredientService recipeUsesIngredientService;
	
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
	public void testUpdateWithOrphanedRui(){
		Recipe recipe = new Recipe();
		recipe.setTitle("Test1");
		recipe.setInstructions("some instructions");
		recipe.setUser(user);
		
		//create a recipe with 1 rui
		Ingredient ingredientOne = ingredientService.read(1);
		MeasurementType mt = measurementTypeService.read(1);
		RecipeUsesIngredient rui = new RecipeUsesIngredient();
		rui.setIngredient(ingredientOne);
		rui.setMeasurementType(mt);
		recipe.getRecipeUsesIngredients().add(rui);
		Recipe result = recipeService.create(recipe);
		
		assertNotNull(rui.getId());		
		assertNotNull(result.getId());
		deleteMeId = result.getId();
		
		//remove the rui
		result.setTitle("Test2");
		result.getRecipeUsesIngredients().remove(0);
		
		Recipe updateResult = recipeService.update(result);
		assertNotNull(updateResult);
		assertEquals(updateResult.getId(), result.getId());
		assertEquals("Test2", updateResult.getTitle());
		assertTrue(updateResult.getRecipeUsesIngredients().size() == 0);
		
		RecipeUsesIngredient thisShouldNotExistAnymore = recipeUsesIngredientService.read(rui.getId());
		assertNull(thisShouldNotExistAnymore);
		
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
	
	@Test
	public void testCupboardQuery(){
		List<Ingredient> ingredients = new ArrayList<Ingredient>();
		Ingredient ingredient2 = new Ingredient(); ingredient2.setId(2); ingredients.add(ingredient2);
		Ingredient ingredient3 = new Ingredient(); ingredient3.setId(3); ingredients.add(ingredient3);
		Ingredient ingredient4 = new Ingredient(); ingredient4.setId(4); ingredients.add(ingredient4);
		List<Recipe> results = recipeService.getRecipesThatCloselyMatchIngredientList(ingredients, 2);
		
		
	}
}
