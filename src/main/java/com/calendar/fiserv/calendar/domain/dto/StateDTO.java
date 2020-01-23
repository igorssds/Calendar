package com.calendar.fiserv.calendar.domain.dto;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class StateDTO {

	private Long id;

	@JsonProperty("name")
	@NotEmpty(message = "O Nome do estado deve estar preenchido")
	@Length(max = 150, message = "O nome deve ter um tamanho máximo de 150 caracteres")
	private String name;

	@JsonProperty("code")
	@NotEmpty(message = "O código do estado deve estar preenchido")
	@Length(max = 2, message = "O Codigo deve ter um tamanho máximo de 2 caracteres")
	private String code;

	@JsonProperty("active")
	private char active;
}
