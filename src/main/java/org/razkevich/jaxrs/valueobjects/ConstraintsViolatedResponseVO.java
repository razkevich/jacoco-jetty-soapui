package org.razkevich.jaxrs.valueobjects;

import java.util.ArrayList;
import java.util.List;

public class ConstraintsViolatedResponseVO extends CommonResponseVO {

	private List<ViolatedConstraint> violatedConstraints = new ArrayList<>();

	public List<ViolatedConstraint> getViolatedConstraints() {
		return violatedConstraints;
	}

	public void setViolatedConstraints(List<ViolatedConstraint> violatedConstraints) {
		this.violatedConstraints = violatedConstraints;
	}

	public static class ViolatedConstraint {
		private String propertyPath;
		private String message;
		private Object wrongValue;

		public ViolatedConstraint(String propertyPath, String message, Object wrongValue) {
			this.propertyPath = propertyPath;
			this.message = message;
			this.wrongValue = wrongValue;
		}

		public ViolatedConstraint() {
		}

		public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
		}

		public Object getWrongValue() {
			return wrongValue;
		}

		public void setWrongValue(Object wrongValue) {
			this.wrongValue = wrongValue;
		}

		public String getPropertyPath() {
			return propertyPath;
		}

		public void setPropertyPath(String propertyPath) {
			this.propertyPath = propertyPath;
		}
	}
}
