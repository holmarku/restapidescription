package de.holmarku.restapidescription.model;

import java.util.*;

import javax.persistence.*;
import javax.persistence.Entity;

import org.hibernate.annotations.*;
import org.openxava.annotations.*;

@Entity
public class ApiDescription {
	
	@Id  // The number property is the key property. Keys are required by default
	@Hidden // The property is not shown to the user. It's an internal identifier
    @GeneratedValue(generator="system-uuid") // Universally Unique Identifier (1)
    @GenericGenerator(name="system-uuid", strategy = "uuid")
	@Column(length=32)  // The column length is used at the UI level and the DB level
    private String apiId;
    
    @ManyToOne(fetch=FetchType.LAZY)
    @Required
    @DescriptionsList
    private OpenApiVersion openApiVersion;
    
    @Column(length=50)  // The column length is used at the UI level and the DB level
    @Required  // A validation error will be shown if the name property is left empty
    private String apiTitle;
    
    @Stereotype("MEMO")
    @Column(length=250)  // The column length is used at the UI level and the DB level
    @Required  // A validation error will be shown if the name property is left empty
    private String apiDescription;

    @ManyToOne(fetch=FetchType.LAZY)
    @Required
    @DescriptionsList
    private ContactEmail contactEmail;
    
    @ManyToOne(fetch=FetchType.LAZY)
    @Required
    @DescriptionsList
    private ApiVersion apiVersion;
    
    @ManyToOne(fetch=FetchType.LAZY)
    @Required
    @DescriptionsList
    private LicenseName licenseName;
    
    @Enumerated(EnumType.STRING)
    @Column(length=10)  // The column length is used at the UI level and the DB level
    @Required  // A validation error will be shown if the name property is left empty
    private ProductEnum productUrl; //server
    
    @Column(length=50)  // The column length is used at the UI level and the DB level
    @Required  // A validation error will be shown if the name property is left empty
    private String apiUrl; //server
    
    @ElementCollection
    @ListProperties("apiPath, apiMethods.methodId, apiMethods.methodEnum, apiMethods.methodOperation")
    private Collection<ApiPath> apiPath;

	public String getApiId() {
		return apiId;
	}

	public void setApiId(String apiId) {
		this.apiId = apiId;
	}

	public OpenApiVersion getOpenApiVersion() {
		return openApiVersion;
	}

	public void setOpenApiVersion(OpenApiVersion openApiVersion) {
		this.openApiVersion = openApiVersion;
	}

	public String getApiTitle() {
		return apiTitle;
	}

	public void setApiTitle(String apiTitle) {
		this.apiTitle = apiTitle;
	}

	public String getApiDescription() {
		return apiDescription;
	}

	public void setApiDescription(String apiDescription) {
		this.apiDescription = apiDescription;
	}

	public ContactEmail getContactEmail() {
		return contactEmail;
	}

	public void setContactEmail(ContactEmail contactEmail) {
		this.contactEmail = contactEmail;
	}

	public ApiVersion getApiVersion() {
		return apiVersion;
	}

	public void setApiVersion(ApiVersion apiVersion) {
		this.apiVersion = apiVersion;
	}

	public LicenseName getLicenseName() {
		return licenseName;
	}

	public void setLicenseName(LicenseName licenseName) {
		this.licenseName = licenseName;
	}

	public ProductEnum getProductUrl() {
		return productUrl;
	}

	public void setProductUrl(ProductEnum productUrl) {
		this.productUrl = productUrl;
	}

	public String getApiUrl() {
		return apiUrl;
	}

	public void setApiUrl(String apiUrl) {
		this.apiUrl = apiUrl;
	}

	public Collection<ApiPath> getApiPath() {
		return apiPath;
	}

	public void setApiPath(Collection<ApiPath> apiPath) {
		this.apiPath = apiPath;
	}


    
	
}
