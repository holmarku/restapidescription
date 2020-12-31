package de.holmarku.restapidescription.model;

import javax.persistence.*;

@Embeddable
public class SubSchemaElements2 {
	 
	private String ciField;

    @ManyToOne(fetch = FetchType.EAGER, optional = true)
    private ApiField apiField;

    @ManyToOne(fetch = FetchType.EAGER, optional = true)
    private ApiSubSchema3 apiSubSchema3;
    
    @Enumerated(EnumType.STRING)
    @Column(length=6)  // The column length is used at the UI level and the DB level
    private ParamIn paramIn;
    
    @Column(length=50)  // The column length is used at the UI level and the DB level
    private Boolean requiredFlag;

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

	public ParamIn getParamIn() {
		return paramIn;
	}

	public void setParamIn(ParamIn paramIn) {
		this.paramIn = paramIn;
	}

	public Boolean getRequiredFlag() {
		return requiredFlag;
	}

	public void setRequiredFlag(Boolean requiredFlag) {
		this.requiredFlag = requiredFlag;
	}

	public ApiSubSchema3 getApiSubSchema3() {
		return apiSubSchema3;
	}

	public void setApiSubSchema3(ApiSubSchema3 apiSubSchema3) {
		this.apiSubSchema3 = apiSubSchema3;
	} 
    
	

}
