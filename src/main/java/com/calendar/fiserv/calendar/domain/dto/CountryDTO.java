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
public class CountryDTO {

	private Long id;

	@JsonProperty("name")
	@NotBlank(message = "O nome não pode ser nulo ou vazio")
	@Length
	private String name;

	@JsonProperty("code")
	@Length(max = 2, message = "O codigo deve ter um tamanho de até dois caracteres")
	private String code;

	@JsonProperty("active")
	private char active;

	@JsonProperty("hasState")
	private char hasState;
}
