package org.razkevich.business.services;

import org.razkevich.business.model.FileTransferRq;
import org.razkevich.business.model.TfsRuntimeException;
import org.razkevich.persistence.model.TfsRsProcessorKey;

public interface TfsService {

	/**
	 * @param data         file transfer data for SendFileInfoNfType tfs request infill
	 * @param processorKey tfs response processor key
	 * @return tfs operation uid
	 * @see ru.sbrf.ufs.eu.tfs.SendFileInfoNfType
	 */
	String transferFile(FileTransferRq data, TfsRsProcessorKey processorKey) throws TfsRuntimeException;
}
