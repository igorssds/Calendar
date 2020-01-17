package com.calendar.fiserv.calendar.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class HolliDayDateDTO {
	
	private Long day;

	private Long month;

	private Long year;

	private char active;
	
	@JsonProperty(value = "state")
	private StateDTO state;
	
	@JsonProperty(value = "city")
	private CityDTO city;
	
	@JsonProperty(value = "country")
	private CountryDTO country;
	
	@JsonProperty(value = "holliday")
	private HolliDayDTO holliday;
	
}
