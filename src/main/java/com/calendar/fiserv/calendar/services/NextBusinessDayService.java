package com.calendar.fiserv.calendar.services;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.calendar.fiserv.calendar.controllers.dto.NextBusinessDayDTO;
import com.calendar.fiserv.calendar.controllers.form.NextBusinessDayRequestForm;
import com.calendar.fiserv.calendar.domain.EHolliDayDate;
import com.calendar.fiserv.calendar.repositories.HollidayDateRepository;

@Service
public class NextBusinessDayService {

	@Autowired
	private HollidayDateRepository holidayDateRepository;

	public NextBusinessDayDTO getNextBusinessDay(NextBusinessDayRequestForm requestForm) {

		LocalDate dateAnalyzed = requestForm.getDate().plusDays(requestForm.getSla());
		return calcNextBusinessDay(dateAnalyzed, StringNormalizer.toPlainTextUpperCase(requestForm.getCityName()),
				requestForm.getStateCode());
	}

	private NextBusinessDayDTO calcNextBusinessDay(LocalDate dateAnalyzed, String cityName, String stateCode) {

		if (!isSunday(dateAnalyzed) && !isHoliday(dateAnalyzed, cityName, stateCode)) {
			return NextBusinessDayDTO.builder().nextBusinessDay(dateAnalyzed).build();
		} else {
			return calcNextBusinessDay(dateAnalyzed.plusDays(1), cityName, stateCode);
		}
	}

	private boolean isHoliday(LocalDate dateAnalyzed, String cityName, String stateCode) {
		Optional<EHolliDayDate> optional = holidayDateRepository.findByDate(dateAnalyzed.getYear(),
				dateAnalyzed.getMonthValue(), dateAnalyzed.getDayOfMonth());
		if (optional.isPresent()) {

			EHolliDayDate holiday = optional.get();

			if (isNationalHoliday(holiday, cityName, stateCode)) {
				return true;
			}

			if (isStateHoliday(holiday, cityName, stateCode)) {
				return true;
			}

			if (isMunicipalHoliday(holiday, cityName, stateCode)) {
				return true;
			}

			return false;

		} else {
			return false;
		}
	}

	private boolean isMunicipalHoliday(EHolliDayDate holiday, String cityName, String stateCode) {
		return holiday.getState() != null && holiday.getState().getCode().equals(stateCode) && holiday.getCity() != null
				&& holiday.getCity().getName().equals(cityName);

	}

	private boolean isStateHoliday(EHolliDayDate holiday, String cityName, String stateCode) {
		return holiday.getCity() == null && cityName.trim().isEmpty() && holiday.getState() != null
				&& holiday.getState().getCode().equals(stateCode);
	}

	private boolean isNationalHoliday(EHolliDayDate holiday, String cityName, String stateCode) {
		return holiday.getCity() == null && holiday.getState() == null && cityName.trim().isEmpty()
				&& stateCode.trim().isEmpty();
	}

	private boolean isSunday(LocalDate dateAnalyzed) {
		return dateAnalyzed.getDayOfWeek() == DayOfWeek.SUNDAY;
	}

}
