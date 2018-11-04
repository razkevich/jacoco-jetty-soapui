package org.razkevich.jaxrs.valueobjects;


public class OpenConfirmationSessionResponseVO extends CommonResponseVO {

	private String smsSessionId;

	public String getSmsSessionId() {
		return smsSessionId;
	}

	public void setSmsSessionId(String smsSessionId) {
		this.smsSessionId = smsSessionId;
	}
}
