package com.calendar.fiserv.calendar.domain.dto;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class StateDTO {
	
	@JsonProperty("name")
	@NotBlank
	@Length(max = 150)
	private String name;
	
	@JsonProperty("code")
	@Length(max = 2)
	private String code;
	
	@JsonProperty("active")
	@NotBlank
	@Length(max = 1)
	private char active;
}
