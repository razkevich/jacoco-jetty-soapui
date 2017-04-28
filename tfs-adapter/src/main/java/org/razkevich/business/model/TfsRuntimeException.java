package org.razkevich.business.model;

public class TfsRuntimeException extends RuntimeException {

	public TfsRuntimeException(String message) {
		super(message);
	}

	public TfsRuntimeException(String message, Exception e) {
		super(message, e);
	}

	public TfsRuntimeException(Exception e) {
		super(e);
	}
}
