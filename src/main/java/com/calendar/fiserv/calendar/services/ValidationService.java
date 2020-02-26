package com.calendar.fiserv.calendar.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.calendar.fiserv.calendar.controllers.exception.InvalidHolidayException;
import com.calendar.fiserv.calendar.controllers.form.HolidayForm;
import com.calendar.fiserv.calendar.model.EHoliday;
import com.calendar.fiserv.calendar.repositories.HolidayRepository;
import com.calendar.fiserv.calendar.services.dto.ValidationResult;

@Service
public class ValidationService {

	@Autowired
	private HolidayRepository holidayRepository;

	public void validate(HolidayForm holiday) {
		
		ValidationResult result = new ValidationResult();
		
		if(holiday.getName() == null || holiday.getName().trim().isEmpty()) {
			result.addInvalidCause("Empty holiday name");
		}

		if (holiday.getDate() == null) {
			result.addInvalidCause("Invalid or impossible date");
		}

		if (!holiday.municipal() && !holiday.state() && !holiday.federal()) {
			result.addInvalidCause("Invalid location description");
		}

		if (exists(holiday)) {
			result.addInvalidCause("Holiday has already been registered");
		}

		if (!result.isValid()) {
			throw new InvalidHolidayException(result.getCauses());
		}
	}

	private boolean exists(HolidayForm holiday) {

		List<EHoliday> list = holidayRepository.exists(StringNormalizer.toPlainTextUpperCase(holiday.getName()), holiday.getDate(),
				StringNormalizer.toPlainTextUpperCase(holiday.getCityName()),
				StringNormalizer.toPlainTextUpperCase(holiday.getStateCode()),
				StringNormalizer.toPlainTextUpperCase(holiday.getCountryCode())

		);
		
		return !list.isEmpty();

	}

}
