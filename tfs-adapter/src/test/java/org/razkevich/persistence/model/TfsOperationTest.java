package org.razkevich.persistence.model;

import org.junit.Test;
import org.razkevich.business.model.TfsRuntimeException;

public class TfsOperationTest {
	@Test(expected = TfsRuntimeException.class)
	public void testTfsOperationConstructor1() {
		TfsOperation tfsOperation = new TfsOperation(null, null, null);
	}

	@Test(expected = TfsRuntimeException.class)
	public void testTfsOperationConstructor2() {
		TfsOperation tfsOperation = new TfsOperation("", "", null);
	}

	@Test(expected = TfsRuntimeException.class)
	public void testTfsOperationConstructor3() {
		TfsOperation tfsOperation = new TfsOperation("", null, new TfsRsProcessorKey(null, null, null));
	}

}