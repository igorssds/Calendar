package com.calendar.fiserv.calendar.domain.dto;

import java.util.List;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class CityDTO {
	
	@JsonProperty("name")
	@NotBlank
	@Length(max = 150)
	private String name;

	@JsonProperty("active")
	@NotBlank
	@Length(max = 1)
	private char active;
	
	List<HolliDayDateDTO> holliDayDate;
}
