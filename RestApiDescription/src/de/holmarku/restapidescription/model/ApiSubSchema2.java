package de.holmarku.restapidescription.model;

import java.util.*;

import javax.persistence.*;
import javax.persistence.Entity;

import org.hibernate.annotations.*;
import org.openxava.annotations.*;

@Entity
public class ApiSubSchema2 {
	
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
    @ListProperties("apiSubSchema3.schemaId, apiSubSchema3.name, apiField.fieldId, apiField.name, paramIn, requiredFlag, ciField")
    private Collection<SubSchemaElements2> subSchemaElements2;

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

	public Collection<SubSchemaElements2> getSubSchemaElements2() {
		return subSchemaElements2;
	}

	public void setSubSchemaElements2(Collection<SubSchemaElements2> subSchemaElements2) {
		this.subSchemaElements2 = subSchemaElements2;
	}

}
