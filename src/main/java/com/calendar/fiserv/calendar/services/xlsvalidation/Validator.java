package com.calendar.fiserv.calendar.services.xlsvalidation;

@FunctionalInterface
public interface Validator {
	
	/**
	 * 
	 * @return
	 */
	public ValidatedCell valid() throws Exception;
	
}
