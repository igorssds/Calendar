package com.calendar.fiserv.calendar.services.xlsvalidation;

import java.util.List;

import org.apache.poi.ss.usermodel.Row;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class InvalidRow {

	private List<InvalidColumn> columnNumber;
	private Row row;

}
