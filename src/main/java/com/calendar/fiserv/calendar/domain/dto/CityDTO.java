package com.calendar.fiserv.calendar.domain.dto;

import java.util.List;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

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
	@NotBlank(message = "O ativo não pode ser nulo ou vazio")
	@Length(max = 1, message = "O ativo deve conter apenas um caractere")
	private char active;

	List<HolliDayDateDTO> holliDayDate;
}
