package com.calendar.fiserv.calendar.services.dto;

import com.calendar.fiserv.calendar.controllers.form.HolidayForm;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class InvalidHoliday {

	private final HolidayForm holiday;
	private final String causes;

}
