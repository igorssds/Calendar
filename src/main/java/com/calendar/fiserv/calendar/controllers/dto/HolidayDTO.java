package com.calendar.fiserv.calendar.controllers.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.calendar.fiserv.calendar.model.EHoliday;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class HolidayDTO {

	private Long id;
	private String name;
	private LocalDate date;
	private String cityName;
	private String stateName;
	private String stateCode;
	private String countryName;
	private String countryCode;
	private boolean active;
	private LocalDateTime creationDate;
	private LocalDateTime updateDate;

	public HolidayDTO(EHoliday holiday) {
		this.id = holiday.getId();
		this.name = holiday.getName();
		this.date = holiday.getDate();
		this.cityName = holiday.getCityName();
		this.stateName = holiday.getStateName();
		this.stateCode = holiday.getStateCode();
		this.countryName = holiday.getCountryName();
		this.countryCode = holiday.getCountryCode();
		this.active = holiday.isActive();
		this.creationDate = holiday.getCreationDate();
		this.updateDate = holiday.getUpdateDate();
	}

}
