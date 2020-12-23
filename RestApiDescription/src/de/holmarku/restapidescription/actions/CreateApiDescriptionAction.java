package de.holmarku.restapidescription.actions;

import java.io.*;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

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

}
