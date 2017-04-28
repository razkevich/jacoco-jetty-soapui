package org.razkevich.business.services;

import org.razkevich.business.model.FileTransferRq;
import org.razkevich.persistence.model.TfsRsProcessorKey;
import ru.sbrf.ufs.eu.tfs.*;
import org.razkevich.business.model.TfsRuntimeException;
import org.razkevich.persistence.model.TfsOperation;
import org.razkevich.persistence.services.TfsPersistenceService;
import org.razkevich.utils.TfsUtils;
import ru.sbrf.ufs.integration.jms.JMSService;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;

public class TfsServiceImpl<Rq extends Serializable, Rs extends Serializable> implements TfsService {

	private final JMSService<Rq, Rs> jmsClient;
	private final TfsPersistenceService persistenceService;

	public TfsServiceImpl(JMSService<Rq, Rs> jmsClient, TfsPersistenceService persistenceService) {
		this.jmsClient = jmsClient;
		this.persistenceService = persistenceService;
	}

	@SuppressWarnings("unchecked")
	@Override
	public String transferFile(final FileTransferRq data, final TfsRsProcessorKey processorKey) throws TfsRuntimeException {
		// [] tfs file transfer request
		SendFileInfoNfType request = new SendFileInfoNfType() {{
			setRqUID(TfsUtils.generateUUID());
			setDestination(new DestinationType() {{
				setName(data.getDestinationName());
				setVersion(data.getDestinationVersion());
				setFile(new ArrayList<FileType>() {{
					add(new FileType() {{
						setFolder(new FolderType() {{
							setFolderSource(data.getSrcFolder());
							setFolderTarget(data.getTargetFolder());
						}});
						setFileInfo(new FileInfoType() {{
							setSize(data.getFileSize());
							setName(data.getFguid());
							setBusinessName(data.getBusinessName());
							setCheckSum(data.getCheckSum());
						}});
						setScenarioInfo(new ScenarioInfoType());
						getScenarioInfo().setScenarioId(processorKey.getTfsScenarioId());
					}});
				}});
				setRqTm(TfsUtils.toXMLCalendar(Calendar.getInstance()));
				setSPName(data.getSpName());
				setSystemId(data.getSystemId());
			}});
		}};
		// [] tfs call
		jmsClient.push((Rq) request);
		// [] tfs operation persistence
		persistenceService.createTfsOperation(new TfsOperation(request.getRqUID(), data.getFguid(), processorKey));
		// [] tfs operation uid as a response
		return request.getRqUID();
	}
}
