package org.razkevich.persistence.services;

import org.razkevich.persistence.model.TfsOperation;

public interface TfsPersistenceService {

	void createTfsOperation(TfsOperation data);

	void updateTfsOperation(TfsOperation data);

	TfsOperation getTfsOperation(String rqUID);
}
