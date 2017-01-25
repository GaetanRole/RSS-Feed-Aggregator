package javaproject.epitech.eu;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;

/**
 * Let the servlet container know where to find our REST goodness.
 * 
 * It'll be deployed at /api, the WADL is available at api/application.wadl
 */
@ApplicationPath("/")
public class ApplicationConfig extends ResourceConfig {
	public ApplicationConfig() {
		packages("javaproject.epitech.eu");
	}
}
