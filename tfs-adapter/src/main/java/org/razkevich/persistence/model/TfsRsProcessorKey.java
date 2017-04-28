package org.razkevich.persistence.model;

import ru.sbrf.ufs.eu.tfs.BasicRqType;

public class TfsRsProcessorKey {

	private final Class<? extends BasicRqType> tfsRsClass;
	private final String tfsScenarioId;
	private final String businessProcessName;

	public TfsRsProcessorKey(Class<? extends BasicRqType> tfsRsClass,
							 String tfsScenarioId,
							 String businessProcessName) {
		this.tfsRsClass = tfsRsClass;
		this.tfsScenarioId = tfsScenarioId;
		this.businessProcessName = businessProcessName;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		TfsRsProcessorKey that = (TfsRsProcessorKey) o;
		if (tfsRsClass != null ? !tfsRsClass.equals(that.tfsRsClass) : that.tfsRsClass != null)
			return false;
		if (tfsScenarioId != null ? !tfsScenarioId.equals(that.tfsScenarioId) : that.tfsScenarioId != null)
			return false;
		return businessProcessName != null ? businessProcessName.equals(that.businessProcessName) : that.businessProcessName == null;
	}

	@Override
	public int hashCode() {
		int result = tfsRsClass != null ? tfsRsClass.hashCode() : 0;
		result = 31 * result + (tfsScenarioId != null ? tfsScenarioId.hashCode() : 0);
		result = 31 * result + (businessProcessName != null ? businessProcessName.hashCode() : 0);
		return result;
	}

	public Class<? extends BasicRqType> getTfsRsClass() {
		return tfsRsClass;
	}

	public String getTfsScenarioId() {
		return tfsScenarioId;
	}

	public String getBusinessProcessName() {
		return businessProcessName;
	}
}
