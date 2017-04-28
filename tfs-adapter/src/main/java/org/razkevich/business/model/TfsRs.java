package org.razkevich.business.model;

import org.razkevich.persistence.model.TfsOperationStatus;

public class TfsRs {

	private final String rqUID;
	private final TfsOperationStatus status;

	public TfsRs(String rqUID, TfsOperationStatus status) {
		this.rqUID = rqUID;
		this.status = status;
	}

	public String getRqUID() {
		return rqUID;
	}

	public TfsOperationStatus getStatus() {
		return status;
	}
}
