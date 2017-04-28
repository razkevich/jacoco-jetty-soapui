package ru.sbrf.ufs.mwp.it;

import com.eviware.soapui.DefaultSoapUICore;
import com.eviware.soapui.SoapUI;
import com.eviware.soapui.impl.WorkspaceImpl;
import com.eviware.soapui.impl.wsdl.WsdlProject;
import com.eviware.soapui.impl.wsdl.teststeps.JdbcTestStepResult;
import com.eviware.soapui.impl.wsdl.teststeps.RestRequestStepResult;
import com.eviware.soapui.impl.wsdl.teststeps.WsdlTestRequestStepResult;
import com.eviware.soapui.model.iface.MessageExchange;
import com.eviware.soapui.model.support.PropertiesMap;
import com.eviware.soapui.model.testsuite.TestCase;
import com.eviware.soapui.model.testsuite.TestCaseRunner;
import com.eviware.soapui.model.testsuite.TestRunner;
import com.eviware.soapui.model.testsuite.TestStepResult;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.File;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Optional;
import java.util.stream.Collectors;

public class SoapUIIT {

	private static final String SOAPUI_LOG_ROOT_PROPERTY = "soapui.logroot";
	private static final String SOAPUI_LOG_ROOT_DIR = "target/";
	private static final String SOAPUI_TESTS_DIR = "target/test-classes";
	private static final String SOAPUI_SETTINGS_FILE = "target/test-classes/conf/soapui-settings.xml";
	private static final Logger LOGGER = LogManager.getLogger(SoapUIIT.class);

	@BeforeTest
	public void init() {
		System.getProperties().setProperty(SOAPUI_LOG_ROOT_PROPERTY, SOAPUI_LOG_ROOT_DIR);
		SoapUI.setSoapUICore(new DefaultSoapUICore(null, SOAPUI_SETTINGS_FILE));
	}

	@DataProvider(name = "testCases")
	public Iterator<Object[]> initTestCases() {
		return FileUtils.listFiles(new File(SOAPUI_TESTS_DIR), null, false).stream()
				.filter(file -> !file.isDirectory())
				.filter(file -> "xml".equals(FilenameUtils.getExtension(file.getName())))
				.flatMap(file -> new WsdlProject(file.getPath(), (WorkspaceImpl) null).getTestSuiteList().stream())
				.flatMap(ts -> ts.getTestCaseList().stream())
				.filter(tc -> Optional.ofNullable(tc).map(t -> !t.isDisabled()).orElse(false))
				.map(tc -> new Object[]{tc})
				.collect(Collectors.toList())
				.iterator();
	}

	@Test(dataProvider = "testCases")
	public void runSoapUITest(TestCase testCase) {
		Optional.of(testCase)
				.map(tc -> tc.run(new PropertiesMap(), false))
				.map(SoapUIIT::logResult)
				.filter(testRunner -> TestRunner.Status.FAILED.equals(testRunner.getStatus()))
				.ifPresent(testRunner -> Assert.fail(String.format(
						"\nSoapUI Test Failed \nTestSuite: %s\nTestCase: %s\nReason: %s",
						testCase.getTestSuite().getName(),
						testCase.getName(),
						testRunner.getReason())));
	}


	private static TestCaseRunner logResult(TestCaseRunner testRunner) {
		for (TestStepResult result : testRunner.getResults()) {
			boolean isWsdl = result instanceof WsdlTestRequestStepResult;
			boolean isRest = result instanceof RestRequestStepResult;
			boolean isJdbc = result instanceof JdbcTestStepResult;
			StringBuilder message = new StringBuilder(String.format(
					"Step result %s -> %s -> %s: %s",
					result.getTestStep().getTestCase().getTestSuite().getName(),
					result.getTestStep().getTestCase().getName(),
					result.getTestStep().getName(),
					result.getStatus()));
			message.append(String.format(
					"\nMessages:\n%s",
					ArrayUtils.isEmpty(result.getMessages()) ? "none" : StringUtils.join(result.getMessages(), '\n')));
			if (isWsdl || isRest || isJdbc) {
				MessageExchange messageExchange = (MessageExchange) result;
				message.append(String.format(
						"\nEndPoint: %s\nRequest:\n%s\nResponse:\n%s",
						isWsdl || isRest ? isRest ? messageExchange.getProperties().get("URL") : messageExchange.getEndpoint() : "-",
						isWsdl ? messageExchange.getRequestContentAsXml() : messageExchange.getRequestContent(),
						isRest ? messageExchange.getResponseContent() : messageExchange.getResponseContentAsXml()));
			}

			LOGGER.log(Arrays
					.asList(TestStepResult.TestStepStatus.OK, TestStepResult.TestStepStatus.UNKNOWN)
					.contains(result.getStatus()) ? Level.INFO : Level.ERROR, message);

		}
		return testRunner;
	}
}
