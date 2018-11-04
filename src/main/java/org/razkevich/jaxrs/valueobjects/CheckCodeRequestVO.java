package org.razkevich.jaxrs.valueobjects;

public class CheckCodeRequestVO {

	private String smsCode;
	private String smsSessionId;
	private String operationId;
	private String ignoredField;

	public String getSmsCode() {
		return smsCode;
	}

	public void setSmsCode(String smsCode) {
		this.smsCode = smsCode;
	}

	public String getSmsSessionId() {
		return smsSessionId;
	}

	public void setSmsSessionId(String smsSessionId) {
		this.smsSessionId = smsSessionId;
	}

	public String getOperationId() {
		return operationId;
	}

	public void setOperationId(String operationId) {
		this.operationId = operationId;
	}

	public String getIgnoredField() {
		return ignoredField;
	}

	public void setIgnoredField(String ignoredField) {
		this.ignoredField = ignoredField;
	}
}
