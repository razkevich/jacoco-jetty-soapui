package org.razkevich.services;

import org.razkevich.jaxrs.valueobjects.*;

import java.util.UUID;

public class ConfirmationServiceImpl implements ConfirmationService {

	@Override
	public OpenConfirmationSessionResponseVO openConfirmationSession(OpenConfirmationSessionRequestVO request) {
		OpenConfirmationSessionResponseVO response = new OpenConfirmationSessionResponseVO();
		response.setSmsSessionId(UUID.randomUUID().toString().replace("-", ""));
		return response;
	}

	@Override
	public CommonResponseVO sendSms(SendSmsRequestVO request) {
		return new CommonResponseVO();
	}

	@Override
	public CommonResponseVO checkCode(CheckCodeRequestVO request) {
		return new CommonResponseVO();
	}

	@Override
	public ConstraintsViolatedResponseVO testRequest(TestRequestVO request) {
		return new ConstraintsViolatedResponseVO();
	}
}
