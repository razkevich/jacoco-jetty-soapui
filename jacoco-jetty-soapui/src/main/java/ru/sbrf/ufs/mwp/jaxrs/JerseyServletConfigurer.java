package ru.sbrf.ufs.mwp.jaxrs;

import org.glassfish.jersey.server.ResourceConfig;
import ru.sbrf.ufs.mwp.services.ConfirmationServiceImpl;

public class JerseyServletConfigurer extends ResourceConfig {

	public JerseyServletConfigurer() {
		register(ConfirmationServiceImpl.class);
	}
}
