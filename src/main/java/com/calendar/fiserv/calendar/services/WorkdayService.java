package com.calendar.fiserv.calendar.services;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.calendar.fiserv.calendar.controllers.dto.WorkdayDTO;
import com.calendar.fiserv.calendar.model.EHoliday;
import com.calendar.fiserv.calendar.repositories.HolidayRepository;

@Service
public class WorkdayService {

	@Autowired
	private HolidayRepository holidayRepository;

	public WorkdayDTO getNextWorkday(LocalDate baseDate, int sla, String cityName, String stateCode,
			String countryCode) {

		LocalDate testDate = baseDate.plusDays(sla);
		return calcNextBusinessDay(testDate, cityName, stateCode, countryCode);
	}

	private WorkdayDTO calcNextBusinessDay(LocalDate testDate, String cityName, String stateCode, String countryCode) {
		if (!isHoliday(testDate, cityName, stateCode, countryCode) && !isSunday(testDate)) {
			return WorkdayDTO.builder().date(testDate).build();
		} else {
			return calcNextBusinessDay(testDate.plusDays(1), cityName, stateCode, countryCode);
		}
	}

	private boolean isSunday(LocalDate testDate) {
		return testDate.getDayOfWeek() == DayOfWeek.SUNDAY;
	}

	private boolean isHoliday(LocalDate testDate, String cityName, String stateCode, String countryCode) {
		List<EHoliday> holidays = holidayRepository.findByDateAndLocation(testDate,
				StringNormalizer.toPlainTextUpperCase(cityName), StringNormalizer.toPlainTextUpperCase(stateCode),
				StringNormalizer.toPlainTextUpperCase(countryCode));

		return !holidays.isEmpty();
	}

}
