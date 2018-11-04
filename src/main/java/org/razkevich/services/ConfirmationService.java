package org.razkevich.services;

import org.razkevich.jaxrs.valueobjects.*;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("/confirmation")
@Produces(APPLICATION_JSON + "; charset=UTF-8")
@Consumes(APPLICATION_JSON)
public interface ConfirmationService {

	@POST
	@Path("/openConfirmationSession")
	OpenConfirmationSessionResponseVO openConfirmationSession(OpenConfirmationSessionRequestVO request);

	@POST
	@Path("/sendSms")
	CommonResponseVO sendSms(SendSmsRequestVO request);

	@POST
	@Path("/checkCode")
	CommonResponseVO checkCode(CheckCodeRequestVO request);

	@POST
	@Path("/testRequest")
	ConstraintsViolatedResponseVO testRequest(TestRequestVO request);
}
