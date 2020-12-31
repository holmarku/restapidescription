package de.holmarku.restapidescription.model;

import javax.persistence.*;

import org.openxava.annotations.*;

@Embeddable
public class ApiPath {
	
	/*
	@Id  // The number property is the key property. Keys are required by default
	@Hidden // The property is not shown to the user. It's an internal identifier
    @GeneratedValue(generator="system-uuid") // Universally Unique Identifier (1)
    @GenericGenerator(name="system-uuid", strategy = "uuid")
	@Column(length=32)  // The column length is used at the UI level and the DB level
    private String apiPathId;
    */
	
    //@Column(length=50)  // The column length is used at the UI level and the DB level
    @Required  // A validation error will be shown if the name property is left empty
    private String apiPath;
    
    //@ElementCollection
    //@ListProperties("methodId, methodEnum, methodOperation")
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    private ApiMethods apiMethods;

	public String getApiPath() {
		return apiPath;
	}

	public void setApiPath(String apiPath) {
		this.apiPath = apiPath;
	}

	public ApiMethods getApiMethods() {
		return apiMethods;
	}

	public void setApiMethods(ApiMethods apiMethods) {
		this.apiMethods = apiMethods;
	}
	
	
}
