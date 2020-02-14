package com.calendar.fiserv.calendar.services.xlsvalidation;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class InvalidColumn {

	private int columnIndex;
	private String cause;

}
