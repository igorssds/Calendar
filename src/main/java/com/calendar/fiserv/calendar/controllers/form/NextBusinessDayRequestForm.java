package com.calendar.fiserv.calendar.controllers.form;

import java.time.LocalDate;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NextBusinessDayRequestForm {

	@NotNull
	private LocalDate date;

	@NotNull
	@Min(value = 0)
	private Integer sla;

	@NotNull
	private String cityName;

	@NotNull
	private String stateCode;

}
