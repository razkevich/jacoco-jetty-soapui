package ru.sbrf.ufs.mwp.jaxrs.valueobjects;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class CommonResponseVO {

	private Integer resultCode = 0;
	private String resultDescription;

	public Integer getResultCode() {
		return resultCode;
	}

	public void setResultCode(Integer resultCode) {
		this.resultCode = resultCode;
	}

	public String getResultDescription() {
		return resultDescription;
	}

	public void setResultDescription(String resultDescription) {
		this.resultDescription = resultDescription;
	}

	@JsonIgnore
	public void setResult(ConfirmationErrorCodeEnum confirmationErrorCode) {
		setResult(confirmationErrorCode.getCode(), confirmationErrorCode.getDescription());
	}

	@JsonIgnore
	public void setResult(Integer resultCode, String resultDescription) {
		setResultCode(resultCode);
		setResultDescription(resultDescription);
	}
}
