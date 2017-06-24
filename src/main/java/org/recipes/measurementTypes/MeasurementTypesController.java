package org.recipes.measurementTypes;

import java.util.List;

import org.recipes.util.AutoCompleteDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Handles requests for the application home page.
 */
@Controller
@RequestMapping(value = "rest/measurementTypes")
public class MeasurementTypesController {
	
	private static final Logger logger = LoggerFactory.getLogger(MeasurementTypesController.class);
	@Autowired
	MeasurementTypeService measurementTypeService;

	
	@RequestMapping(value="" , method = RequestMethod.POST)
	public  @ResponseBody MeasurementType create(@RequestBody MeasurementType measurementType){
		MeasurementType result = measurementTypeService.create(measurementType);
		return result;
	}
	
	@RequestMapping(value="", method=RequestMethod.GET)
	public @ResponseBody List<MeasurementType> readAll(){
		List<MeasurementType> results = measurementTypeService.readAll();
		return results;
		//List<AutoCompleteDTO> autoCompleteResults = AutoCompleteDTO.transform(results);
		//return autoCompleteResults;
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public  @ResponseBody MeasurementType read(@PathVariable Integer id){
		MeasurementType result = measurementTypeService.read(id);
		return result;
	}
	
	@RequestMapping(value="", method=RequestMethod.PUT)
	public  @ResponseBody MeasurementType update(MeasurementType measurementType){
		MeasurementType result = measurementTypeService.update(measurementType);
		return result;
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
	public void delete(@PathVariable Integer id){
		measurementTypeService.delete(id);
	}
	
}
