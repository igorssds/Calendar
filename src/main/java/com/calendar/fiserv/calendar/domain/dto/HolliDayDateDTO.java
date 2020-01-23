package com.calendar.fiserv.calendar.domain.dto;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class HolliDayDateDTO {

	@NotNull(message = "Dia não pode ser nulo")
	@Max(value = 31, message = "O dia não pode ser maior que 31")
	private Long day;

	@NotNull
	@Max(value = 12 , message = "O mês não pode ser maior que 12")
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
