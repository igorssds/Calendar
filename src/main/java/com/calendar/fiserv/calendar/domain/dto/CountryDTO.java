package com.calendar.fiserv.calendar.domain.dto;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class CountryDTO {

	@JsonProperty("name")
	@NotBlank
	@Length
	private String name;

	@JsonProperty("code")
	@Length(max = 2)
	private String code;

	@JsonProperty("active")
	@Length(max = 1)
	private char active;
	
	@JsonProperty("hasState")
	@Length(max = 1)
	private char hasState;
}
