package com.calendar.fiserv.calendar.services.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class HolliDayDateRemoveDTO {

	private Long day;

	private Long month;

	private Long countryId;

	private Long holliDayId;

}
