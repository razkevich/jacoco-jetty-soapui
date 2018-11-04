package org.razkevich.jaxrs;

import org.glassfish.jersey.server.ResourceConfig;
import org.razkevich.services.ConfirmationServiceImpl;

public class JerseyServletConfigurer extends ResourceConfig {

	public JerseyServletConfigurer() {
		register(ConfirmationServiceImpl.class);
	}
}
