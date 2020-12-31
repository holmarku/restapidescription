package de.holmarku.restapidescription.model;

import java.util.*;

import javax.persistence.*;
import javax.persistence.Entity;

import org.hibernate.annotations.*;
import org.openxava.annotations.*;

@Entity
public class ApiMethods {
	
	@Id  // The number property is the key property. Keys are required by default
	@Hidden // The property is not shown to the user. It's an internal identifier
    @GeneratedValue(generator="system-uuid") // Universally Unique Identifier (1)
    @GenericGenerator(name="system-uuid", strategy = "uuid")
	@Column(length=32)  // The column length is used at the UI level and the DB level
    private String methodId;
	
    @Enumerated(EnumType.STRING)
    @Column(length=10)  // The column length is used at the UI level and the DB level
    @Required  // A validation error will be shown if the name property is left empty
    private MethodEnum methodEnum;
    
    @Column(length=50)  // The column length is used at the UI level and the DB level
    @Required  // A validation error will be shown if the name property is left empty
    private String methodSummary;
    
    @Column(length=50)  // The column length is used at the UI level and the DB level
    @Required  // A validation error will be shown if the name property is left empty
    private String methodOperation;
    
    @Column(length=50)  // The column length is used at the UI level and the DB level
    private String methodTag;
    
    @ElementCollection
    @ListProperties("apiField.fieldId, apiField.name, paramIn, requiredFlag, ciField")
    private Collection<ApiElements> parameterElements; 
    
    @Column(length=50)  // The column length is used at the UI level and the DB level
    private String requestBodyDescription;
    
    @ElementCollection
    @ListProperties("apiSchema.schemaId, apiSchema.name, paramIn")
    private Collection<ApiElements> requestBodySchema;
    
    @ElementCollection
    @ListProperties("responseEnum, responseDescription, responseSchema.schemaId, responseSchema.name")
    private Collection<ResponseElements> responseElements;

	public String getMethodId() {
		return methodId;
	}

	public void setMethodId(String methodId) {
		this.methodId = methodId;
	}

	public MethodEnum getMethodEnum() {
		return methodEnum;
	}

	public void setMethodEnum(MethodEnum methodEnum) {
		this.methodEnum = methodEnum;
	}

	public String getMethodSummary() {
		return methodSummary;
	}

	public void setMethodSummary(String methodSummary) {
		this.methodSummary = methodSummary;
	}

	public String getMethodOperation() {
		return methodOperation;
	}

	public void setMethodOperation(String methodOperation) {
		this.methodOperation = methodOperation;
	}

	public Collection<ApiElements> getParameterElements() {
		return parameterElements;
	}

	public String getMethodTag() {
		return methodTag;
	}

	public void setMethodTag(String methodTag) {
		this.methodTag = methodTag;
	}

	public void setParameterElements(Collection<ApiElements> parameterElements) {
		this.parameterElements = parameterElements;
	}

	public String getRequestBodyDescription() {
		return requestBodyDescription;
	}

	public void setRequestBodyDescription(String requestBodyDescription) {
		this.requestBodyDescription = requestBodyDescription;
	}

	public Collection<ApiElements> getRequestBodySchema() {
		return requestBodySchema;
	}

	public void setRequestBodySchema(Collection<ApiElements> requestBodySchema) {
		this.requestBodySchema = requestBodySchema;
	}

	public Collection<ResponseElements> getResponseElements() {
		return responseElements;
	}

	public void setResponseElements(Collection<ResponseElements> responseElements) {
		this.responseElements = responseElements;
	} 
    
    
	
}
