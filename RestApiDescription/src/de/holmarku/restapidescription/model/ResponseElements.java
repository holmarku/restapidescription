package de.holmarku.restapidescription.model;

import javax.persistence.*;

import org.openxava.annotations.*;

@Embeddable
public class ResponseElements {
	
    @Enumerated(EnumType.STRING)
    @Column(length=25)  // The column length is used at the UI level and the DB level
    @Required  // A validation error will be shown if the name property is left empty
    private ResponseEnum responseEnum;
    
    @Stereotype("MEMO")
    @Column(length=50)  // The column length is used at the UI level and the DB level
    @Required  // A validation error will be shown if the name property is left empty
    private String responseDescription;
    
    @ManyToOne(fetch = FetchType.EAGER, optional = true)
    private ApiSchema responseSchema;

	public ResponseEnum getResponseEnum() {
		return responseEnum;
	}

	public void setResponseEnum(ResponseEnum responseEnum) {
		this.responseEnum = responseEnum;
	}

	public String getResponseDescription() {
		return responseDescription;
	}

	public void setResponseDescription(String responseDescription) {
		this.responseDescription = responseDescription;
	}

	public ApiSchema getResponseSchema() {
		return responseSchema;
	}

	public void setResponseSchema(ApiSchema responseSchema) {
		this.responseSchema = responseSchema;
	}

}
