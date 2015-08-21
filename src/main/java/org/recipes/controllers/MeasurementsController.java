package org.recipes.controllers;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.recipes.autocomplete.AJAXUtils;
import org.recipes.autocomplete.AutoComplete;
import org.recipes.dto.MeasurementtypeDTO;
import org.recipes.dto.UserDTO;
import org.recipes.services.MeasurementsService;
import org.recipes.web.SessionConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Controller
@RequestMapping(value="rest/measurements")
public class MeasurementsController {

	@Autowired
	MeasurementsService measurementsService;
	
	@RequestMapping(value="" , method=RequestMethod.GET)
	public @ResponseBody List<Map<String,String>> getMeasurements(){
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		HttpServletRequest request = attr.getRequest();
		HttpSession session =  request.getSession(true); // true == allow create
		UserDTO loggedInUser = (UserDTO)session.getAttribute(SessionConstants.USER);
		if (loggedInUser != null) {
			List<AutoComplete> results = measurementsService.getMeasurements();			
			List<Map<String,String>> acResults = AJAXUtils.getAutoCompleteAJAX(results);
			return acResults;
		}else {
			return null; //??
		}
	}
	
	@RequestMapping(value="" , method=RequestMethod.PUT)	
	public @ResponseBody MeasurementtypeDTO modifyMeasurement(@RequestBody MeasurementtypeDTO measurementForm) {
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		HttpServletRequest request = attr.getRequest();
		HttpSession session =  request.getSession(true); // true == allow create
		UserDTO loggedInUser = (UserDTO)session.getAttribute(SessionConstants.USER);
		if (loggedInUser != null) {
			return measurementsService.createOrModifyMeasurement(measurementForm);			
		}else {
			return null;
		}		
	}
	
	@RequestMapping(value="", method=RequestMethod.POST)
	public @ResponseBody MeasurementtypeDTO createMeasurement(@RequestBody MeasurementtypeDTO measurementForm) {
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		HttpServletRequest request = attr.getRequest();
		HttpSession session =  request.getSession(true); // true == allow create
		UserDTO loggedInUser = (UserDTO)session.getAttribute(SessionConstants.USER);
		if (loggedInUser != null) {
			return measurementsService.createOrModifyMeasurement(measurementForm);
			
		}else {
			return null;
		}
	}
	
	@RequestMapping(value="", method=RequestMethod.DELETE)
	public @ResponseBody boolean deleteMeasurement(@RequestBody MeasurementtypeDTO measurementForm) {
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		HttpServletRequest request = attr.getRequest();
		HttpSession session =  request.getSession(true); // true == allow create
		UserDTO loggedInUser = (UserDTO)session.getAttribute(SessionConstants.USER);
		if (loggedInUser != null) {
			return measurementsService.deleteMeasurement(measurementForm);
			
		}else {
			return false;
		}		
	}
	
}
