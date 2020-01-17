package com.calendar.fiserv.calendar.domain.dto;

import java.util.List;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class HolliDayDTO {

	@JsonProperty("name")
	@NotBlank
	@Length(max = 150)
	private String name;

	@JsonProperty("active")
	@NotBlank
	@Length(max = 1)
	private char active;
	
	@JsonIgnore
	List<HolliDayDateDTO> holliDayDate;
}
