package ru.sbrf.ufs.mwp.jaxrs.valueobjects;

public enum ConfirmationErrorCodeEnum {

	CODE_CHECK_FAILURE(-1, "Code check failed"),
	CONSTRAINTS_VIOLATED(-2, "Constraints violated");

	private Integer code;
	private String description;

	ConfirmationErrorCodeEnum(Integer code, String description) {
		this.code = code;
		this.description = description;
	}

	public Integer getCode() {
		return code;
	}

	public String getDescription() {
		return description;
	}
}
