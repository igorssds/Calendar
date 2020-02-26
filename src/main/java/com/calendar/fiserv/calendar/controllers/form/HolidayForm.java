package com.calendar.fiserv.calendar.controllers.form;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.calendar.fiserv.calendar.model.EHoliday;
import com.calendar.fiserv.calendar.services.StringNormalizer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HolidayForm {

	@NotEmpty
	private String name;
	@NotNull
	private LocalDate date;
	private String cityName;
	private String stateName;
	private String stateCode;
	@NotEmpty
	private String countryName;
	@NotEmpty
	private String countryCode;
	@NotNull
	private boolean active;

	public EHoliday toHoliday() {
		return EHoliday.builder().name(name).date(date).cityName(cityName).stateName(stateName)
				.stateCode(StringNormalizer.toPlainTextUpperCase(stateCode)).countryName(countryName)
				.countryCode(StringNormalizer.toPlainTextUpperCase(countryCode)).active(active)
				.namePlainText(StringNormalizer.toPlainTextUpperCase(name)).creationDate(LocalDateTime.now())
				.updateDate(LocalDateTime.now()).cityNamePlainText(StringNormalizer.toPlainTextUpperCase(cityName))
				.build();
	}

	public void update(EHoliday holiday) {
		holiday.setName(name);
		holiday.setNamePlainText(StringNormalizer.toPlainTextUpperCase(name));
		holiday.setDate(date);
		holiday.setCityName(cityName);
		holiday.setCityNamePlainText(StringNormalizer.toPlainTextUpperCase(cityName));
		holiday.setStateCode(StringNormalizer.toPlainTextUpperCase(stateCode));
		holiday.setStateName(stateName);
		holiday.setCountryCode(StringNormalizer.toPlainTextUpperCase(countryCode));
		holiday.setCountryName(countryName);
		holiday.setActive(active);
		holiday.setUpdateDate(LocalDateTime.now());
	}

	public boolean municipal() {

		return cityName != null && !cityName.trim().isEmpty()

				&& stateName != null && !stateName.trim().isEmpty()

				&& stateCode != null && !stateCode.trim().isEmpty()

				&& countryName != null && !countryName.trim().isEmpty()

				&& countryCode != null && !countryCode.trim().isEmpty();
	}

	public boolean state() {

		return (cityName == null || cityName.trim().isEmpty())

				&& stateName != null && !stateName.trim().isEmpty()

				&& stateCode != null && !stateCode.trim().isEmpty()

				&& countryName != null && !countryName.trim().isEmpty()

				&& countryCode != null && !countryCode.trim().isEmpty();
	}

	public boolean federal() {

		return (cityName == null || cityName.trim().isEmpty())

				&& (stateName == null || stateName.trim().isEmpty())

				&& (stateCode == null || stateName.trim().isEmpty())

				&& countryName != null && !countryName.trim().isEmpty()

				&& countryCode != null && !countryCode.trim().isEmpty();
	}
}
