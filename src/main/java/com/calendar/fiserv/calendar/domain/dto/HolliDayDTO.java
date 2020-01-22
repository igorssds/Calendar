package com.calendar.fiserv.calendar.domain.dto;

import java.util.List;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sun.istack.internal.NotNull;

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
	@NotNull
	@Length(max = 1, message = "O Ativo deve conter apenas um caractere")
	private char active;

	@JsonIgnore
	List<HolliDayDateDTO> holliDayDate;
}
