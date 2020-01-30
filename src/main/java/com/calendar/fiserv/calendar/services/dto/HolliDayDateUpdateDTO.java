package com.calendar.fiserv.calendar.services.dto;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

import com.calendar.fiserv.calendar.domain.dto.HolliDayDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class HolliDayDateUpdateDTO {

	@Valid
	private HolliDayDateIdDTO id;

	@NotNull(message = "Dia não pode ser nulo")
	@Max(value = 31, message = "O dia não pode ser maior que 31")
	private Long day;

	@NotNull
	@Max(value = 12, message = "O mês não pode ser maior que 12")
	private Long month;

	private Long year;

	private char active;

	private HolliDayDTO holliDay;
}
