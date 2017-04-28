package org.razkevich.jms.server;

import org.razkevich.business.model.TfsRs;
import org.razkevich.business.model.TfsRsProcessor;
import org.razkevich.persistence.model.TfsOperation;
import org.razkevich.persistence.model.TfsOperationStatus;
import org.razkevich.persistence.model.TfsRsProcessorKey;
import ru.sbrf.ufs.eu.tfs.BasicRqType;
import ru.sbrf.ufs.eu.tfs.FileStatusType;
import ru.sbrf.ufs.eu.tfs.SendFileStatusNfType;
import org.razkevich.business.model.TfsRuntimeException;
import org.razkevich.persistence.services.TfsPersistenceService;
import ru.sbrf.ufs.integration.jms.JMSServer;
import ru.sbrf.ufs.integration.jms.RequestProcessor;

import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.xml.bind.JAXBContext;
import java.io.Serializable;
import java.util.Map;

public class TfsServer<Rq extends BasicRqType, Rs extends Serializable> extends JMSServer<Rq, Rs> {

	TfsServer(ConnectionFactory connectionFactory,
			  Destination requestDestination,
			  JAXBContext jaxbContext,
			  final Map<TfsRsProcessorKey, TfsRsProcessor> processors,
			  final TfsPersistenceService persistenceService) {
		super(connectionFactory, requestDestination, null, jaxbContext);
		setRequestProcessor(new RequestProcessor<Rq, Rs>() {
			@Override
			public Rs process(Rq response) {
				try {
					String rqUID = response.getRqUID();
					TfsOperation tfsOperation = persistenceService.getTfsOperation(rqUID);
					TfsOperationStatus status = determineStatus(response, tfsOperation);
					tfsOperation.setStatus(status);
					persistenceService.updateTfsOperation(tfsOperation);
					processors.get(tfsOperation.getTfsRsProcessorKey()).process(new TfsRs(rqUID, status));    // todo: [] what is its reference type?
				} catch (Exception e) {
					throw new TfsRuntimeException("Response processing failed", e);
				}
				return null;
			}
		});
	}

	private static TfsOperationStatus determineStatus(BasicRqType response, TfsOperation tfsOperation) {
		if (response instanceof SendFileStatusNfType) {
			for (FileStatusType fileStatus : ((SendFileStatusNfType) response).getFileStatus()) {
				if (tfsOperation.getFileUID().equals(fileStatus.getFileInfo().getName())
						&& fileStatus.getStatus().getStatusCode() != 0) {
					return TfsOperationStatus.COMPLETED_WITH_ERROR;
				}
			}
		}
		return TfsOperationStatus.COMPLETED;
	}
}
