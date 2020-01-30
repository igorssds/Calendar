package com.calendar.fiserv.calendar.services.dto;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class HolliDayDateIdDTO {
	
	@NotNull(message = "O id do country não pode ser nulo")
	private Long countryId;

	@NotNull(message = "Dia não pode ser nulo")
	private Long dayId;

	@NotNull(message = "Mês não pode ser nulo")
	private Long monthId;

	@NotNull(message = "O id do holliDay não pode ser nulo")
	private Long holliDayId;

}
