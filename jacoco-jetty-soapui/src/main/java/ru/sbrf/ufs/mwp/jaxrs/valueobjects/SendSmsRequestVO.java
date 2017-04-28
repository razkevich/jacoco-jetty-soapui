package ru.sbrf.ufs.mwp.jaxrs.valueobjects;

public class SendSmsRequestVO {

	private String smsTemplate;
	private String phoneNumber;
	private String smsSessionId;

	public String getSmsTemplate() {
		return smsTemplate;
	}

	public void setSmsTemplate(String smsTemplate) {
		this.smsTemplate = smsTemplate;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getSmsSessionId() {
		return smsSessionId;
	}

	public void setSmsSessionId(String smsSessionId) {
		this.smsSessionId = smsSessionId;
	}
}
