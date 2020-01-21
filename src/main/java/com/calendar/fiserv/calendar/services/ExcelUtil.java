package com.calendar.fiserv.calendar.services;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.calendar.fiserv.calendar.domain.ECity;
import com.calendar.fiserv.calendar.domain.ECountry;
import com.calendar.fiserv.calendar.domain.EHolliDay;
import com.calendar.fiserv.calendar.domain.EHolliDayDate;
import com.calendar.fiserv.calendar.domain.EState;
import com.calendar.fiserv.calendar.repositories.CityRepository;
import com.calendar.fiserv.calendar.repositories.CountryRepository;
import com.calendar.fiserv.calendar.repositories.HolliDayRepository;
import com.calendar.fiserv.calendar.repositories.HollidayDateRepository;
import com.calendar.fiserv.calendar.repositories.StateRepository;

@Service
public class ExcelUtil {

	@Autowired
	private HollidayDateRepository holliDayDateRepository;

	@Autowired
	private HolliDayRepository holliDayRepository;

	@Autowired
	private CountryRepository countryRepository;

	@Autowired
	private StateRepository stateRepository;

	@Autowired
	private CityRepository cityRepository;

	public void readToHolliDays(InputStream stream) throws IOException {

		XSSFWorkbook wb = new XSSFWorkbook(stream);
		XSSFSheet ws = wb.getSheetAt(0);

		for (int i = 1; i < ws.getPhysicalNumberOfRows(); i++) {

			EHolliDayDate holliDayDate = new EHolliDayDate();
			EHolliDay holliDay = new EHolliDay();
			ECity city = new ECity();
			EState state = new EState();
			ECountry country = new ECountry();
			Row row = ws.getRow(i);

			if (row.getCell(0) == null)
				continue;

			holliDayDate.setDay((long) row.getCell(0).getNumericCellValue());

			if (row.getCell(1) == null)
				continue;
			holliDayDate.setMonth((long) row.getCell(1).getNumericCellValue());

			if (row.getCell(2) != null) {
				holliDayDate.setYear((long) row.getCell(2).getNumericCellValue());
			}

			holliDayDate.setActive(validateChar(row.getCell(3).getNumericCellValue()));

			holliDay.setName(row.getCell(4).getStringCellValue());
			holliDay.setActive(validateChar(row.getCell(5).getNumericCellValue()));

			if (row.getCell(6) == null) {
				city = null;
			} else {
				city.setName(row.getCell(6).getStringCellValue().toUpperCase());
				city.setActive(validateChar(row.getCell(7).getNumericCellValue()));
				city.setCreationDate(LocalDateTime.now());
			}

			if (row.getCell(8) == null) {
				state = null;
			} else {
				state.setName(row.getCell(8).getStringCellValue().toUpperCase());
				state.setActive(validateChar(row.getCell(10).getNumericCellValue()));
				state.setCreationDate(LocalDateTime.now());
			}

			if (row.getCell(9) != null)
				state.setCode(row.getCell(9).getStringCellValue());

			if (row.getCell(11) == null)
				continue;

			country.setName(row.getCell(11).getStringCellValue().toUpperCase());
			country.setCode(row.getCell(12).getStringCellValue());
			country.setActive(validateChar(row.getCell(13).getNumericCellValue()));
			country.setHasState(validateChar(row.getCell(14).getNumericCellValue()));
			country.setCreationDate(LocalDateTime.now());

			ECountry countryRecovered = countryRepository.findByName(country.getName());

			if (countryRecovered != null) {
				country = null;
			} else {
				country = countryRepository.save(country);
			}

			EState stateRecovered = null;
			if (state != null)
				stateRecovered = stateRepository.findByName(state.getName());

			if (stateRecovered != null || state == null) {
				state = null;
			} else {
				state.setCountry(country == null ? countryRecovered : country);
				state = stateRepository.save(state);
			}

			ECity cityRecovered = null;
			if (state != null)
				cityRepository.findByName(city.getName());

			if (cityRecovered != null || city == null) {
				city = null;
			} else {
				city.setCountry(country == null ? countryRecovered : country);
				city.setState(state == null ? stateRecovered : state);
				city = cityRepository.save(city);
			}

			holliDay.setCreationDate(LocalDateTime.now());
			holliDay = holliDayRepository.save(holliDay);

			holliDayDate.setHolliday(holliDay);

			if (city == null && cityRecovered == null) {
				holliDayDate.setCity(null);
			} else {
				holliDayDate.setCity(city == null ? cityRecovered : city);
			}

			holliDayDate.setCountry(country == null ? countryRecovered : country);

			if (state == null & stateRecovered == null) {
				holliDayDate.setState(null);
			} else {
				holliDayDate.setState(state == null ? stateRecovered : state);
			}

			holliDayDate.setCreationDate(LocalDateTime.now());
			holliDayDateRepository.save(holliDayDate);
		}
	}

	public char validateChar(double num) {
		return num == 1 ? '1' : '0';
	}
}
