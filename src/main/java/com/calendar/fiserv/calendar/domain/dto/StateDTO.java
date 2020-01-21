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
public class StateDTO {
	
	private Long id;
	
	@JsonProperty("name")
	@NotBlank(message = "O nome não pode estar nulo ou vazio")
	@Length(max = 150 , message = "O nome deve ter um tamanho máximo de 150 caracteres")
	private String name;
	
	@JsonProperty("code")
	@Length(max = 2 , message = "O Codigo deve ter um tamanho máximo de 2 caracteres")
	private String code;
	
	@JsonProperty("active")
	@NotBlank(message = "O Ativo não pode estar nulo")
	@Length(max = 1 , message = "O Ativo deve ter apenas um caractere")
	private char active;
}
