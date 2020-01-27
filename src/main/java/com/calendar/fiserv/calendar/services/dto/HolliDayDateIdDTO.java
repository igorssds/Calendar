package com.calendar.fiserv.calendar.services.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class HolliDayDateIdDTO {

	private Long countryId;

	private Long dayId;

	private Long monthId;

	private Long holliDayId;

}
