package org.recipes.util;

import java.util.ArrayList;
import java.util.List;

import org.recipes.measurementTypes.MeasurementType;

/**
 * JQuery UI expects data provided to its autocomplete lists to be in the format [{label: "", value: {},...].  This DTO will provide that format for the 
 * MeasurementType
 * @author Owner
 *
 */
public class AutoCompleteDTO {

	
	/**
	 * @return the label
	 */
	public String getLabel() {
		return label;
	}
	/**
	 * @param label the label to set
	 */
	public void setLabel(String label) {
		this.label = label;
	}
	/**
	 * @return the value
	 */
	public Object getValue() {
		return value;
	}
	/**
	 * @param value the value to set
	 */
	public void setValue(Object value) {
		this.value = value;
	}
	private String label;
	private Object value;
	
	
	public static List<AutoCompleteDTO> transform (List<? extends AutoComplete> entities){
		List<AutoCompleteDTO> results = new ArrayList<AutoCompleteDTO>(entities.size());
		for (AutoComplete entity : entities){
			AutoCompleteDTO dto = new AutoCompleteDTO();
			dto.setLabel(String.valueOf(entity.getLabel()));
			dto.setValue(entity);
			results.add(dto);
		}
		return results;
	}
	
}
