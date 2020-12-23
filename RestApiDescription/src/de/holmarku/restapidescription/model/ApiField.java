package de.holmarku.restapidescription.model;

import javax.persistence.*;
import javax.persistence.Entity;

import org.hibernate.annotations.*;
import org.openxava.annotations.*;

@Entity
public class ApiField {
	
	
	@Id  // The number property is the key property. Keys are required by default
	@Hidden // The property is not shown to the user. It's an internal identifier
    @GeneratedValue(generator="system-uuid") // Universally Unique Identifier (1)
    @GenericGenerator(name="system-uuid", strategy = "uuid")
	@Column(length=32)  // The column length is used at the UI level and the DB level
    private String fieldId;
 
    @Column(length=50)  // The column length is used at the UI level and the DB level
    @Required  // A validation error will be shown if the name property is left empty
    private String name;
    
    @Stereotype("MEMO")
    @Column(length=1024)  // The column length is used at the UI level and the DB level
    @Required  // A validation error will be shown if the name property is left empty
    private String descriptionDe;
    
    @Stereotype("MEMO")
    @Column(length=1024)  // The column length is used at the UI level and the DB level
    @Required  // A validation error will be shown if the name property is left empty
    private String descriptionEn;
    
    @Enumerated(EnumType.STRING)
    @Column(length=20)  // The column length is used at the UI level and the DB level
    @Required  // A validation error will be shown if the name property is left empty
    private TypeFormat typeFormat;
    
    @Column(length=2)  // The column length is used at the UI level and the DB level
    private String integralDigits;
    
    @Column(length=2)  // The column length is used at the UI level and the DB level
    private String fractionDigits;
    
    @Column(length=20)  // The column length is used at the UI level and the DB level
    private String parameterTable;
    
    @Stereotype("MEMO")
    @Column(length=250)  // The column length is used at the UI level and the DB level
    private String enumValues;
    
    @Column(length=50)  // The column length is used at the UI level and the DB level
    private String defaultValue;
    
    @Column(length=50)  // The column length is used at the UI level and the DB level
    private String exampleValue;
    
    @Column(length=20)  // The column length is used at the UI level and the DB level
    private String minimumVal;
    
    @Column(length=20)  // The column length is used at the UI level and the DB level
    private String maximumVal;

	public String getFieldId() {
		return fieldId;
	}

	public void setFieldId(String fieldId) {
		this.fieldId = fieldId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescriptionDe() {
		return descriptionDe;
	}

	public void setDescriptionDe(String descriptionDe) {
		this.descriptionDe = descriptionDe;
	}

	public String getDescriptionEn() {
		return descriptionEn;
	}

	public void setDescriptionEn(String descriptionEN) {
		this.descriptionEn = descriptionEN;
	}

	public TypeFormat getTypeFormat() {
		return typeFormat;
	}

	public void setTypeFormat(TypeFormat typeFormat) {
		this.typeFormat = typeFormat;
	}

	public String getParameterTable() {
		return parameterTable;
	}

	public void setParameterTable(String parameterTable) {
		this.parameterTable = parameterTable;
	}

	public String getEnumValues() {
		return enumValues;
	}

	public void setEnumValues(String enumValues) {
		this.enumValues = enumValues;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	public String getExampleValue() {
		return exampleValue;
	}

	public void setExampleValue(String exampleValue) {
		this.exampleValue = exampleValue;
	}

	public String getIntegralDigits() {
		return integralDigits;
	}

	public void setIntegralDigits(String integralDigits) {
		this.integralDigits = integralDigits;
	}

	public String getFractionDigits() {
		return fractionDigits;
	}

	public void setFractionDigits(String fractionDigits) {
		this.fractionDigits = fractionDigits;
	}

	public String getMinimumVal() {
		return minimumVal;
	}

	public void setMinimumVal(String minimumVal) {
		this.minimumVal = minimumVal;
	}

	public String getMaximumVal() {
		return maximumVal;
	}

	public void setMaximumVal(String maximumVal) {
		this.maximumVal = maximumVal;
	}
    
	
}
