package org.razkevich.persistence.model;

import org.razkevich.business.model.TfsRuntimeException;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Calendar;

public class TfsOperation implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private Calendar creationDate;
	private Calendar updateDate;
	private TfsOperationStatus status;
	private String rqUID;
	private String fileUID;
	private TfsRsProcessorKey tfsRsProcessorKey;

	public TfsOperation(String rqUID, String fileUID, TfsRsProcessorKey tfsRsProcessorKey) {
		if (Arrays.asList(rqUID, fileUID, tfsRsProcessorKey).contains(null)) {
			throw new TfsRuntimeException("No nulls allowed in constructor");
		}
		this.creationDate = Calendar.getInstance();
		this.status = TfsOperationStatus.SENT;
		this.rqUID = rqUID;
		this.fileUID = fileUID;
		this.tfsRsProcessorKey = tfsRsProcessorKey;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Calendar getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Calendar creationDate) {
		this.creationDate = creationDate;
	}

	public Calendar getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Calendar updateDate) {
		this.updateDate = updateDate;
	}

	public TfsOperationStatus getStatus() {
		return status;
	}

	public void setStatus(TfsOperationStatus status) {
		this.status = status;
	}

	public String getRqUID() {
		return rqUID;
	}

	public void setRqUID(String rqUID) {
		this.rqUID = rqUID;
	}

	public String getFileUID() {
		return fileUID;
	}

	public void setFileUID(String fileUID) {
		this.fileUID = fileUID;
	}

	public TfsRsProcessorKey getTfsRsProcessorKey() {
		return tfsRsProcessorKey;
	}

	public void setTfsRsProcessorKey(TfsRsProcessorKey tfsRsProcessorKey) {
		this.tfsRsProcessorKey = tfsRsProcessorKey;
	}
}
