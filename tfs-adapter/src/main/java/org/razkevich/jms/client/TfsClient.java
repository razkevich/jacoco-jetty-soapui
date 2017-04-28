package org.razkevich.jms.client;

import ru.sbrf.ufs.eu.tfs.BasicRqType;
import ru.sbrf.ufs.integration.jms.JMSClient;

import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.xml.bind.JAXBContext;
import java.io.Serializable;

public class TfsClient<Rq extends BasicRqType, Rs extends Serializable> extends JMSClient<Rq, Rs> {

	protected TfsClient(ConnectionFactory connectionFactory,
						Destination requestDestination,
						Destination responseDestination,
						JAXBContext jaxbContext) {
		super(connectionFactory, requestDestination, responseDestination, jaxbContext);
	}
}
