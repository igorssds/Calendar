package com.calendar.fiserv.calendar.controllers.exception;

import java.io.IOException;
import java.util.List;

import com.calendar.fiserv.calendar.services.XlsService;
import com.calendar.fiserv.calendar.services.dto.InvalidHoliday;

import lombok.Getter;

public class XlsWithErrorsException extends RuntimeException {

	@Getter
	private byte[] xlsFile;

	public XlsWithErrorsException(List<InvalidHoliday> invalidHolidays, XlsService xlsService) {
		try {
			this.xlsFile = xlsService.convertInvalidsToXls(invalidHolidays);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			this.xlsFile = null;
		}
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
