package de.holmarku.restapidescription.model;

import javax.persistence.*;
import javax.persistence.Entity;

import org.hibernate.annotations.*;
import org.openxava.annotations.*;

@Entity
public class LicenseName {

	@Id  // The number property is the key property. Keys are required by default
	@Hidden // The property is not shown to the user. It's an internal identifier
    @GeneratedValue(generator="system-uuid") // Universally Unique Identifier (1)
    @GenericGenerator(name="system-uuid", strategy = "uuid")
	@Column(length=32)  // The column length is used at the UI level and the DB level
    private String licenseNameId;

    @Column(length=50)  // The column length is used at the UI level and the DB level
    @Required  // A validation error will be shown if the name property is left empty
    private String name;

    @Stereotype("MEMO")
    @Column(length=500)  // The column length is used at the UI level and the DB level
    @Required  // A validation error will be shown if the name property is left empty
    private String licenseDescription;
    
	public String getLicenseNameId() {
		return licenseNameId;
	}

	public void setLicenseNameId(String licenseNameId) {
		this.licenseNameId = licenseNameId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLicenseDescription() {
		return licenseDescription;
	}

	public void setLicenseDescription(String licenseDescription) {
		this.licenseDescription = licenseDescription;
	}
    
}
