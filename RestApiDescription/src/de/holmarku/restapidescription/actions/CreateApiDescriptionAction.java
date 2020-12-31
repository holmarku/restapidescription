package de.holmarku.restapidescription.actions;

import java.io.*;
import java.util.*;

import javax.persistence.*;
import javax.swing.*;
import javax.swing.filechooser.*;

import org.openxava.actions.*;
import org.openxava.jpa.*;

import de.holmarku.restapidescription.model.*;

public class CreateApiDescriptionAction extends ViewBaseAction {
	
	@Override
	public void execute() throws Exception {
		
		ApiDescription apiDescription = (ApiDescription) getView().getEntity();
		System.out.println("Decription created for " + apiDescription.getApiTitle());
		
		JFileChooser fc = new JFileChooser();
		fc.setSelectedFile(new File("api.yaml"));
	    fc.setFileFilter( new FileNameExtensionFilter("YAML-Files", "yaml"));

	    int state = fc.showSaveDialog( null );

	    if ( state == JFileChooser.APPROVE_OPTION )
	    {
	      File file = fc.getSelectedFile();
	      System.out.println( file.getName() );
	      if (file.exists()) {
              file.delete();
          }
          try {
              @SuppressWarnings("resource")
              PrintWriter out = new PrintWriter(new FileWriter(file));
              out.println("openapi: " + apiDescription.getOpenApiVersion().getName());
              out.println("info:");
              out.println("  title: " + apiDescription.getApiTitle());
              out.println("  description: " + apiDescription.getApiDescription());
              out.println("  contact:");
              out.println("    email: " + apiDescription.getContactEmail().getName());
              out.println("  license:");
              out.println("    name: " + apiDescription.getLicenseName().getLicenseDescription());
              out.println("servers:");
              out.println("  -url: 'https://{environment}:{port}/api/" + apiDescription.getProductUrl()+"-api/" +
            	apiDescription.getApiUrl() + "/" + apiDescription.getApiVersion().getName() + "'");
              out.println("paths:");

              for (ApiPath apiPath : apiDescription.getApiPath()) {
            	  System.out.println(apiPath.getApiPath());
            	  out.println("  /" + apiPath.getApiPath());            	  
            	  
            	  //ApiMethods apiMethod = apiPath.getApiMethods();
            	  Query query = XPersistence.getManager().createQuery(
            			  "from ApiMethods a where a.methodId=:am");
            	  query.setParameter("am", apiPath.getApiMethods().getMethodId());
            	  ApiMethods apiMethod = (ApiMethods) query.getSingleResult();
            	  System.out.println(apiMethod.getMethodEnum().toString());
            	  out.println("    " + apiMethod.getMethodEnum().toString() + ":");
            	  out.println("      summary: " + apiMethod.getMethodSummary());
            	  out.println("      operationId: " + apiMethod.getMethodOperation());
            	  out.println("      tags:");
            	  out.println("        - " + apiMethod.getMethodTag());
            	  out.println("      parameters:");
            	  
            	  for (ApiElements parameterElements : apiMethod.getParameterElements()) {
            		  if (parameterElements.getApiSchema()!=null) {
            			  out.println("        - $ref: '#/components/parameters/" + parameterElements.getApiSchema().getName() + "'");
            		  }
            		  if (parameterElements.getApiField()!=null) {
            			  out.println("      - name: " + parameterElements.getApiField().getName());
            			  out.println("        in: " + parameterElements.getParamIn().toString());
            			  out.println("        description: >-");
            			  out.println("          EN: " + parameterElements.getApiField().getDescriptionEn());
            			  out.println("          DE: " + parameterElements.getApiField().getDescriptionDe());
            			  out.println("          CI: " + parameterElements.getCiField());
            			  out.println("        required: " + parameterElements.getRequiredFlag());
            			  out.println("        schema:");
            			  String offset = "          ";
        				  schemaTypeResolution(out, parameterElements.getApiField(), offset);
    					  if (parameterElements.getApiField().getDefaultValue() != null) {
    						  out.println("        example: " + parameterElements.getApiField().getExampleValue());
    					  }
            		  }
            	  }
            	  
            	  //requestBody
            	  if (apiMethod.getRequestBodySchema() != null) {
            		  out.println("      requestBody:");
            		  out.println("        description: " + apiMethod.getRequestBodyDescription());
            		  out.println("        content:");
            		  out.println("          application/json:");
        			  out.println("            schema:");
        			  out.println("              allOf:");
        			  for (ApiElements requestBodySchema : apiMethod.getRequestBodySchema()) {
        				  out.println("                - $ref: '#/components/schemas/" + requestBodySchema.getApiSchema().getName());
        			  }
        			  	
            	  }
            	  
            	  //responses
            	  out.println("      responses:");
            	  for (ResponseElements responseElements : apiMethod.getResponseElements()) {
            		 switch(responseElements.getResponseEnum().toString()) {
            	  		case "OK_200":
            	  			out.println("        '200':");
            	  			break;
            	  		case "CREATED_201":
            	  			out.println("        '201':");
            	  			break;
            	  		case "BAD_REQUEST_400":
            	  			out.println("        '400':");
            	  			break;
            	  		case "UNATUHORIZED_401":
            	  			out.println("        '401':");
            	  			break;
            	  		case "FORBIDDEN_403":
            	  			out.println("        '403':");
            	  			break;
            	  		case "NOT_FOUND_404":
            	  			out.println("        '404':");
            	  			break;
            	  		case "INTERNAL_SERVER_ERROR_500":
            	  			out.println("        '500':");
            	  			break;
            	  		}
     	  			out.println("          description: " + responseElements.getResponseDescription());
     	  			if (responseElements.getResponseSchema()!=null) {
	     	  			out.println("          content:");
	     	  			out.println("            application/json:");
	     	  			out.println("              schema:");
	     	  			out.println("                allOf:");
	     	  			out.println("                  - $ref: '#/components/schemas/" + responseElements.getResponseSchema().getName());
     	  			 }
     	  		  }
            	            
	
	              //components parameter
	              out.println("components:");
	              out.println("  parameters:");
	              for (ApiElements parameterElements : apiMethod.getParameterElements()) {
	        		  if (parameterElements.getApiSchema()!=null) {
	        			  for (SchemaElements se : parameterElements.getApiSchema().getSchemaElements()) {
	        				  schemaElementsFieldResolution(out, se);
	        			  }	        			  
	        		  }
	        		  if (parameterElements.getApiField()!=null) {
	        				  apiElementsFieldResolution(out, parameterElements);			  
	        		  }
	              }
	              
	              //schemas
	              List<String> list = new ArrayList<String>();
	              
	              out.println("  schemas:");
	              for (ApiElements ae : apiMethod.getRequestBodySchema()) {       		  
	    			  boolean schemaExists = false;
					  for (String listae : list) {
	    				  if (ae.getApiSchema().getName().equals(listae)) {
	    					  schemaExists=true; //do nothing
	    				  }             				  
	    			  }
					  if (schemaExists==false) {
	    				  list.add(ae.getApiSchema().getName());
	    				  out.println("    " + ae.getApiSchema().getName() + ":");
	    				  out.println("      type: " + ae.getParamIn().toString());
	    				  out.println("      properties:");
	    				  for (SchemaElements se : ae.getApiSchema().getSchemaElements()) {
	    					  if (se.getApiField() != null) {
	    						  String offset = "        ";
	    						  schemaTypeResolution(out, se.getApiField(), offset);
	    					  }
	    					  if (se.getApiSubSchema1() != null) {
	    						  out.println("        " + se.getApiSubSchema1().getName() + ":");
	    	    				  out.println("          allOf:");
	    						  for (SubSchemaElements1 se1 : se.getApiSubSchema1().getSubSchemaElements1()) {
	    							  if (se1.getApiField() != null) {
	    								  out.println("          - $ref: '#/components/schemas/" + se1.getApiField().getName());
	    	    					  }
	    							  if (se1.getApiSubSchema2() != null) {
	    								  out.println("          - $ref: '#/components/schemas/" + se1.getApiSubSchema2().getName());
	    	    					  }
	    						  }
	    						  for (SubSchemaElements1 se1 : se.getApiSubSchema1().getSubSchemaElements1()) {
	    							  if (se1.getApiSubSchema2() != null) {
	    								  
	    								  schemaExists = false;
	    								  for (String listae : list) {
	    				    				  if (se1.getApiSubSchema2().getName().equals(listae)) {
	    				    					  schemaExists=true; //do nothing
	    				    				  }             				  
	    				    			  }
	    								  if (schemaExists==false) {
	    				    				  list.add(se1.getApiSubSchema2().getName());
	    				    				  out.println("    " + se1.getApiSubSchema2().getName() + ":");
	    				    				  out.println("      type: " + se1.getApiSubSchema2().toString());
	    				    				  out.println("      properties:");
	    				    				  for (SubSchemaElements2 se2 : se1.getApiSubSchema2().getSubSchemaElements2()) {
	    				    					  if (se2.getApiField() != null) {
	    				    						  String offset = "            ";
	    				    						  schemaTypeResolution(out, se2.getApiField(), offset);
	    				    					  }
	    				    					  if (se2.getApiSubSchema3() != null) {
	    				    						  out.println("        " + se.getApiSubSchema1().getName() + ":");
	    				    	    				  out.println("          allOf:");
	    				    						  for (SubSchemaElements3 se3 : se2.getApiSubSchema3().getSubSchemaElements3()) {
	    				    							  if (se3.getApiField() != null) {
	    				    								  out.println("          - $ref: '#/components/schemas/" + se3.getApiField().getName());
	    				    	    					  }
	    				    						  }
	    				    					  }
	    				    				  }
	    								  }
	    	    					  }
	    						  }
	    					  }
	    				  }
	    				  
					  }
	              }	            		  
	            		  
              }
      			
              out.flush();
              out.close();
          } catch (IOException e) {
              // TODO Auto-generated catch block
              e.printStackTrace();
          }
	    }
	    else
	      System.out.println( "Auswahl abgebrochen" );
	    
	    
	}

	private void schemaElementsFieldResolution(PrintWriter out, SchemaElements se) {
		String offset = "    ";
		  out.println(offset + se.getApiField().getName() + ":");
		  out.println(offset + "  name: " + se.getApiField().getName());
		  out.println(offset + "  in: " + se.getParamIn().toString());
		  out.println(offset + "  description: >-");
		  out.println(offset + "    EN: " + se.getApiField().getDescriptionEn());
		  out.println(offset + "    DE: " + se.getApiField().getDescriptionDe());
		  out.println(offset + "    CI: " + se.getCiField());
		  out.println(offset + "  required: " + se.getRequiredFlag());
		  out.println(offset + "  schema:");
		  
		  offset = "          ";
		  schemaTypeResolution(out, se.getApiField(), offset);
		  if (se.getApiField().getDefaultValue() != null) {
			  out.println("        example: " + se.getApiField().getExampleValue());
		  }
	}

	private void apiElementsFieldResolution(PrintWriter out, ApiElements apiElements) {
		String offset = "    ";
		  out.println(offset + apiElements.getApiField().getName() + ":");
		  out.println(offset + "  name: " + apiElements.getApiField().getName());
		  out.println(offset + "  in: " + apiElements.getParamIn().toString());
		  out.println(offset + "  description: >-");
		  out.println(offset + "    EN: " + apiElements.getApiField().getDescriptionEn());
		  out.println(offset + "    DE: " + apiElements.getApiField().getDescriptionDe());
		  out.println(offset + "    CI: " + apiElements.getCiField());
		  out.println(offset + "  required: " + apiElements.getRequiredFlag());
		  out.println(offset + "  schema:");
		  
		  offset = "          ";
		  schemaTypeResolution(out, apiElements.getApiField(), offset);
		  if (apiElements.getApiField().getDefaultValue() != null) {
			  out.println("        example: " + apiElements.getApiField().getExampleValue());
		  }
	}
	
	private void schemaTypeResolution(PrintWriter out, ApiField af, String offset) {
		switch(af.getTypeFormat().toString()) {
			  case "Integer_short":
				  out.println(offset + "type: integer"); 
				  out.println(offset + "format: int32");
				  if (af.getDefaultValue() != null) {
					  out.println(offset + "default: " + af.getDefaultValue());
				  }
				  break;
			  case "Integer_long":
				  out.println(offset + "type: integer"); 
				  out.println(offset + "format: int64");
				  if (af.getDefaultValue() != null) {
					  out.println(offset + "default: " + af.getDefaultValue());
				  }
				  break;
			  case "Number_float":
				  out.println(offset + "type: number"); 
				  out.println(offset + "format: float");
				  if (af.getDefaultValue() != null) {
					  out.println(offset + "default: " + af.getDefaultValue());
				  }
				  break;
			  case "Number_double":
				  out.println(offset + "type: number"); 
				  out.println(offset + "format: double");
				  if (af.getDefaultValue() != null) {
					  out.println(offset + "default: " + af.getDefaultValue());
				  }
				  break;
			  case "String":
				  out.println(offset + "type: string"); 
				  if (af.getDefaultValue() != null) {
					  out.println(offset + "default: " + af.getDefaultValue());
				  }
				  break;
			  case "String_byte":
				  out.println(offset + "type: string"); 
				  out.println(offset + "format: byte");
				  if (af.getDefaultValue() != null) {
					  out.println(offset + "default: " + af.getDefaultValue());
				  }
				  break;
			  case "String_binary":
				  out.println(offset + "type: string"); 
				  out.println(offset + "format: binary");
				  if (af.getDefaultValue() != null) {
					  out.println(offset + "default: " + af.getDefaultValue());
				  }
				  break;
			  case "Boolean_value":
				  out.println(offset + "type: boolean"); 
				  if (af.getDefaultValue() != null) {
					  out.println(offset + "default: " + af.getDefaultValue());
				  }
				  break;
			  case "String_date":
				  out.println(offset + "type: string"); 
				  out.println(offset + "format: date");
				  if (af.getDefaultValue() != null) {
					  out.println(offset + "default: " + af.getDefaultValue());
				  }
				  break;
			  case "String_date_time":
				  out.println(offset + "type: string"); 
				  out.println(offset + "format: date-time");
				  if (af.getDefaultValue() != null) {
					  out.println(offset + "default: " + af.getDefaultValue());
				  }
				  break;
			  case "String_password":
				  out.println(offset + "type: string"); 
				  out.println(offset + "format: password");
				  if (af.getDefaultValue() != null) {
					  out.println(offset + "default: " + af.getDefaultValue());
				  }
				  break;
		  }
	}
	

}
