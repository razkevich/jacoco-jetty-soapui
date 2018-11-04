package org.razkevich.ut;

import org.testng.annotations.Test;
import org.razkevich.jaxrs.valueobjects.TestRequestVO;
import org.razkevich.services.ConfirmationServiceImpl;

public class SampleTest {
	@Test
	public void testSample() {
		new ConfirmationServiceImpl().testRequest(new TestRequestVO());
	}
}
