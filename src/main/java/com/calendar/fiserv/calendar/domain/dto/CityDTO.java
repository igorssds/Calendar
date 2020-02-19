package com.calendar.fiserv.calendar.domain.dto;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CityDTO {

	private Long id;

	@JsonProperty("name")
	@NotBlank(message = "O nome não pode ser nulo ou vazio")
	@Length(max = 150, message = "O nome deve ter um tamanho maximo de 150 caracteres")
	private String name;

	@JsonProperty("active")
	private char active;

}
