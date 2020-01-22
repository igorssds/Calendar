package com.calendar.fiserv.calendar.domain.dto;

import javax.validation.Valid;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sun.istack.internal.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class HolliDayDateDTO {

	@NotNull
	private Long day;

	@NotNull
	private Long month;

	private Long year;

	private char active;

	@Valid
	@JsonProperty(value = "state")
	private StateDTO state;

	@Valid
	@JsonProperty(value = "city")
	private CityDTO city;

	@Valid
	@JsonProperty(value = "country")
	private CountryDTO country;

	@Valid
	@JsonProperty(value = "holliday")
	private HolliDayDTO holliday;

}
