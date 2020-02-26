package com.calendar.fiserv.calendar.controllers.exception;

public class InvalidHolidayException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InvalidHolidayException(String cause) {
		super(cause);
	}

}
