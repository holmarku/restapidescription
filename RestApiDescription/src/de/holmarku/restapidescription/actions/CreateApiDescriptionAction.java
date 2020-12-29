package de.holmarku.restapidescription.actions;

import java.io.*;
import java.util.*;

import javax.swing.*;
import javax.swing.filechooser.*;

import org.openxava.actions.*;

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
            	  
            	  ApiMethods apiMethod = apiPath.getApiMethods();
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
        				  schemaResolution(out, parameterElements, offset);
    					  if (parameterElements.getApiField().getDefaultValue() != null) {
    						  out.println("        example: " + parameterElements.getApiField().getExampleValue());
    					  }
            		  }
            	  }
            	  
            	  //requestBody
            	  if (apiMethod.getRequestBodySchema() != null) {
            		  out.println("        description: " + apiMethod.getRequestBodyDescription());
            		  out.println("        content:");
            		  out.println("          application/json:");
        			  out.println("            schema:");
        			  out.println("              allOf:");
        			  for (ApiElements requestBodySchema : apiMethod.getRequestBodySchema()) {
        				  out.println("                - $ref: '#/components/schemas/" + requestBodySchema);
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
     	  			out.println("          description: " + apiMethod.getRequestBodyDescription());
     	  			out.println("          content:");
     	  			out.println("            application/json:");
     	  			out.println("              schema:");
     	  			out.println("                allOf:");
     	  			out.println("                  - $ref: '#/components/schemas/" + responseElements);
            	  }
            	  
            	  
              
	
	              //components parameter
	              out.println("components:");
	              out.println("  parameters:");
	              for (ApiElements parameterElements : apiMethod.getParameterElements()) {
	        		  if (parameterElements.getApiSchema()!=null) {
	        			  for (SchemaElements se : parameterElements.getApiSchema().getSchemaElements()) {
	        				  out.println("    " + se.getApiField().getName() + ":");
		        			  out.println("        name: " + se.getApiField().getName());
	            			  out.println("        in: " + se.getParamIn().toString());
	            			  out.println("        description: >-");
	            			  out.println("          EN: " + se.getApiField().getDescriptionEn());
	            			  out.println("          DE: " + se.getApiField().getDescriptionDe());
	            			  out.println("          CI: " + se.getCiField());
	            			  out.println("        required: " + se.getRequiredFlag());
	            			  out.println("        schema:");
	            			  String offset = "          ";
	        				  schemaResolution(out, parameterElements, offset);
	    					  if (parameterElements.getApiField().getDefaultValue() != null) {
	    						  out.println("        example: " + se.getApiField().getExampleValue());
	    					  }
	    					  
	        			  }	        			  
	        		  }
	              }
	              
	              //schemas
	              
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

	private void schemaResolution(PrintWriter out, ApiElements apiElements, String offset) {
		switch(apiElements.toString()) {
			  case "Integer_short":
				  out.println(offset + "type: integer"); 
				  out.println(offset + "format: int32");
				  if (apiElements.getApiField().getDefaultValue() != null) {
					  out.println(offset + "default: " + apiElements.getApiField().getDefaultValue());
				  }
				  break;
			  case "Integer_long":
				  out.println(offset + "type: integer"); 
				  out.println(offset + "format: int64");
				  if (apiElements.getApiField().getDefaultValue() != null) {
					  out.println(offset + "default: " + apiElements.getApiField().getDefaultValue());
				  }
				  break;
			  case "Number_float":
				  out.println(offset + "type: number"); 
				  out.println(offset + "format: float");
				  if (apiElements.getApiField().getDefaultValue() != null) {
					  out.println(offset + "default: " + apiElements.getApiField().getDefaultValue());
				  }
				  break;
			  case "Number_double":
				  out.println(offset + "type: number"); 
				  out.println(offset + "format: double");
				  if (apiElements.getApiField().getDefaultValue() != null) {
					  out.println(offset + "default: " + apiElements.getApiField().getDefaultValue());
				  }
				  break;
			  case "String":
				  out.println(offset + "type: string"); 
				  if (apiElements.getApiField().getDefaultValue() != null) {
					  out.println(offset + "default: " + apiElements.getApiField().getDefaultValue());
				  }
				  break;
			  case "String_byte":
				  out.println(offset + "type: string"); 
				  out.println(offset + "format: byte");
				  if (apiElements.getApiField().getDefaultValue() != null) {
					  out.println(offset + "default: " + apiElements.getApiField().getDefaultValue());
				  }
				  break;
			  case "String_binary":
				  out.println(offset + "type: string"); 
				  out.println(offset + "format: binary");
				  if (apiElements.getApiField().getDefaultValue() != null) {
					  out.println(offset + "default: " + apiElements.getApiField().getDefaultValue());
				  }
				  break;
			  case "Boolean_value":
				  out.println(offset + "type: boolean"); 
				  if (apiElements.getApiField().getDefaultValue() != null) {
					  out.println(offset + "default: " + apiElements.getApiField().getDefaultValue());
				  }
				  break;
			  case "String_date":
				  out.println(offset + "type: string"); 
				  out.println(offset + "format: date");
				  if (apiElements.getApiField().getDefaultValue() != null) {
					  out.println(offset + "default: " + apiElements.getApiField().getDefaultValue());
				  }
				  break;
			  case "String_date_time":
				  out.println(offset + "type: string"); 
				  out.println(offset + "format: date-time");
				  if (apiElements.getApiField().getDefaultValue() != null) {
					  out.println(offset + "default: " + apiElements.getApiField().getDefaultValue());
				  }
				  break;
			  case "String_password":
				  out.println(offset + "type: string"); 
				  out.println(offset + "format: password");
				  if (apiElements.getApiField().getDefaultValue() != null) {
					  out.println(offset + "default: " + apiElements.getApiField().getDefaultValue());
				  }
				  break;
		  }
	}
	

}
