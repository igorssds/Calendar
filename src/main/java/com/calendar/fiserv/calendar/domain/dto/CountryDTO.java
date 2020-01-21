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
public class CountryDTO {

	@JsonProperty("name")
	@NotBlank(message = "O nome não pode ser nulo ou vazio")
	@Length
	private String name;

	@JsonProperty("code")
	@Length(max = 2 , message = "O codigo deve ter um tamanho de até dois caracteres")
	private String code;

	@JsonProperty("active")
	@Length(max = 1 , message = "O Ativo deve conter apenas um caracter")
	private char active;
	
	@JsonProperty("hasState")
	@Length(max = 1)
	private char hasState;
}
