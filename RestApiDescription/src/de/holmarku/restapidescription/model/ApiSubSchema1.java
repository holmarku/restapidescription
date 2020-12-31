package de.holmarku.restapidescription.model;

import java.util.*;

import javax.persistence.*;
import javax.persistence.Entity;

import org.hibernate.annotations.*;
import org.openxava.annotations.*;

@Entity
public class ApiSubSchema1 {
	
	@Id  // The number property is the key property. Keys are required by default
	@Hidden // The property is not shown to the user. It's an internal identifier
    @GeneratedValue(generator="system-uuid") // Universally Unique Identifier (1)
    @GenericGenerator(name="system-uuid", strategy = "uuid")
	@Column(length=32)  // The column length is used at the UI level and the DB level
    private String schemaId;
 
    @Column(length=50)  // The column length is used at the UI level and the DB level
    @Required  // A validation error will be shown if the name property is left empty
    private String name;
    
    @ElementCollection
    @ListProperties("apiSubSchema2.schemaId, apiSubSchema2.name, apiField.fieldId, apiField.name, paramIn, requiredFlag, ciField")
    private Collection<SubSchemaElements1> subSchemaElements1;

	public String getSchemaId() {
		return schemaId;
	}

	public void setSchemaId(String schemaId) {
		this.schemaId = schemaId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Collection<SubSchemaElements1> getSubSchemaElements1() {
		return subSchemaElements1;
	}

	public void setSubSchemaElements1(Collection<SubSchemaElements1> subSchemaElements1) {
		this.subSchemaElements1 = subSchemaElements1;
	}

 
    

}
