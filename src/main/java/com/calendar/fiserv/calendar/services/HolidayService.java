package com.calendar.fiserv.calendar.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.calendar.fiserv.calendar.controllers.dto.HolidayDTO;
import com.calendar.fiserv.calendar.controllers.exception.InvalidHolidayException;
import com.calendar.fiserv.calendar.controllers.exception.XlsWithErrorsException;
import com.calendar.fiserv.calendar.controllers.form.HolidayForm;
import com.calendar.fiserv.calendar.model.EHoliday;
import com.calendar.fiserv.calendar.repositories.HolidayRepository;
import com.calendar.fiserv.calendar.services.dto.InvalidHoliday;

@Service
public class HolidayService {

	@Autowired
	private HolidayRepository repository;

	@Autowired
	private XlsService xlsService;

	@Autowired
	private ValidationService validationService;

	@Transactional
	public HolidayDTO create(HolidayForm form) {
		validationService.validate(form);
		EHoliday holiday = repository.save(form.toHoliday());
		return new HolidayDTO(holiday);
	}

	public Optional<HolidayDTO> read(Long id) {
		Optional<EHoliday> optional = repository.findById(id);
		return optional.isPresent() ? Optional.of(new HolidayDTO(optional.get())) : Optional.empty();
	}

	@Transactional
	public void delete(Long id) {
		repository.deleteById(id);
	}

	public List<HolidayDTO> findAllHolidays() {
		List<EHoliday> holidays = repository.findAllHolidays();
		return holidays.stream().map(holiday -> new HolidayDTO(holiday)).collect(Collectors.toList());
	}

	@Transactional
	public void update(Long id, HolidayForm form) {
		Optional<EHoliday> holiday = repository.findById(id);
		if(holiday.isPresent()) {
			validationService.validate(form);
			form.update(holiday.get());
		} else {
			throw new EntityNotFoundException();
		}

	}

	public void createFromXlsFile(MultipartFile file) throws IOException {
		List<HolidayForm> holidays = xlsService.extractHolidays(file);
		List<InvalidHoliday> invalidHolidays = new ArrayList<>();

		holidays.stream().forEach((holiday) -> {
			try {
				create(holiday);
			} catch (InvalidHolidayException exception) {
				invalidHolidays.add(new InvalidHoliday(holiday, exception.getMessage()));
			}
		});

		if (!invalidHolidays.isEmpty()) {
			throw new XlsWithErrorsException(invalidHolidays, xlsService);
		}
	}

	public byte[] readAllToXls() throws IOException {
		return xlsService.convertToXls(repository.findAllHolidays());
	}

}
