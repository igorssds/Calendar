package com.calendar.fiserv.calendar.domain.dto;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class HolliDayDTO {

	private Long id;

	@JsonProperty("name")
	@NotBlank(message = "O nome não pode ser nulo ou vazio")
	@Length(max = 150)
	private String name;

	@JsonProperty("active")
	private char active;

}
