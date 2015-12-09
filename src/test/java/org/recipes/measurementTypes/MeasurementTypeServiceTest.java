package org.recipes.measurementTypes;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import junit.framework.TestCase;
@RunWith( SpringJUnit4ClassRunner.class )
@ContextConfiguration(locations={"classpath:root-context.xml"})
public class MeasurementTypeServiceTest extends TestCase{

	@Autowired
	MeasurementTypeService measurementTypeService;
	Integer deleteMeId=null;
	@After    
    public void tearDown()
    {
        if (deleteMeId != null) {
            measurementTypeService.delete(deleteMeId);//make sure our test cases are marked deleted
        }
    }
	@Before
    public  void setup()
    {	
        deleteMeId = null;
    }
    
	@Test
	public void testCreate() {
		MeasurementType measurementType = new MeasurementType();
		measurementType.setTitle("Test1");
		measurementType.setDescription("desc");
		MeasurementType result = measurementTypeService.create(measurementType);
		assertNotNull(result);
		assertNotNull(result.getId());
		deleteMeId = result.getId();
	}
	
	//this test case is intended to be run after the script in sql.txt
	@Test
	public void testRead() {
		MeasurementType result = measurementTypeService.read(1);
		assertNotNull(result);
		assertNotNull(result.getId());		
	}
	
	@Test
	public void testUpdate() {
		MeasurementType measurementType = new MeasurementType();
		measurementType.setTitle("Test1");
		measurementType.setDescription("desc");
		MeasurementType result = measurementTypeService.create(measurementType);
		
		assertNotNull(result.getId());
		deleteMeId = result.getId();
		
		result.setTitle("Test2");
		MeasurementType updateResult = measurementTypeService.update(result);
		assertNotNull(updateResult);
		assertEquals(updateResult.getId(), result.getId());
		assertEquals("Test2", updateResult.getTitle());		
		
	}

	@Test	
	public void testDelete() {
		MeasurementType measurementType = new MeasurementType();
		measurementType.setTitle("Test1");
		measurementType.setDescription("desc");
		MeasurementType measurementType2 = measurementTypeService.create(measurementType);
		boolean result = measurementTypeService.delete(measurementType2.getId());
		assertTrue(result);
		
		MeasurementType result2 = measurementTypeService.read(measurementType2.getId());
		assertNull(result2);
	}
	
}
