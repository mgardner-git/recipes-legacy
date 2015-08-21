package org.recipes.dto;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.recipes.model.Measurementtype;

import org.recipes.autocomplete.AutoComplete;

public class MeasurementtypeDTO implements AutoComplete{

	private int id;
	private String description;
	private String title;
	
	public MeasurementtypeDTO(){}
	public MeasurementtypeDTO(Measurementtype entity){
		this.id = entity.getId();
		this.description = entity.getDescription();
		this.title = entity.getTitle();
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	
	@JsonIgnore
	public String getLabel() {return title;}
	@JsonIgnore
	public String getValue() {return String.valueOf(id);}
	
	public Measurementtype constructEntity(){
		Measurementtype mtEntity = new Measurementtype();
		mtEntity.setId(id);
		mtEntity.setTitle(title);
		mtEntity.setDescription(description);
		return mtEntity;
	}
}
