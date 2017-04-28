package ru.sbrf.ufs.mwp.ut;

import org.testng.annotations.Test;
import ru.sbrf.ufs.mwp.jaxrs.valueobjects.TestRequestVO;
import ru.sbrf.ufs.mwp.services.ConfirmationServiceImpl;

public class SampleTest {
	@Test
	public void testSample() {
		new ConfirmationServiceImpl().testRequest(new TestRequestVO());
	}
}
