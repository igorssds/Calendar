package com.calendar.fiserv.calendar.services.xlsvalidation;

public class ValidatedCell {

	private final boolean valid;
	private final String causeOfInvalid;

	public ValidatedCell(String causeOfInvalid) {
		this.valid = false;
		this.causeOfInvalid = causeOfInvalid;
	}

	public ValidatedCell() {
		this.valid = true;
		this.causeOfInvalid = "";
	}

	public boolean isValid() {
		return valid;
	}

	public String getCauseOfInvalid() {
		return causeOfInvalid;
	}

}
