package de.holmarku.restapidescription.model;

import javax.persistence.*;

@Embeddable
public class SchemaElements {
	 
    private String ciField;
    
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private ApiField apiField;

	public ApiField getApiField() {
		return apiField;
	}
	
	public void setApiField(ApiField apiField) {
		this.apiField = apiField;
	}

	public String getCiField() {
		return ciField;
	}

	public void setCiField(String ciField) {
		this.ciField = ciField;
	} 
    

}
