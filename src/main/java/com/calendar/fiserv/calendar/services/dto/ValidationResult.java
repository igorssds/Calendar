package com.calendar.fiserv.calendar.services.dto;

import java.util.ArrayList;
import java.util.List;

public class ValidationResult {

	private List<String> invalidCauses = new ArrayList<>();

	public boolean isValid() {
		return invalidCauses.isEmpty();
	}

	public void addInvalidCause(String cause) {
		invalidCauses.add(cause);
	}

	public String getCauses() {
		return String.join("; ", this.invalidCauses);
	}

}
