package com.calendar.fiserv.calendar.services;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.calendar.fiserv.calendar.domain.ECity;
import com.calendar.fiserv.calendar.domain.ECountry;
import com.calendar.fiserv.calendar.domain.EHolliDay;
import com.calendar.fiserv.calendar.domain.EHolliDayDate;
import com.calendar.fiserv.calendar.domain.EState;
import com.calendar.fiserv.calendar.domain.dto.HolliDayDateDTO;
import com.calendar.fiserv.calendar.repositories.CityRepository;
import com.calendar.fiserv.calendar.repositories.CountryRepository;
import com.calendar.fiserv.calendar.repositories.HolliDayRepository;
import com.calendar.fiserv.calendar.repositories.HollidayDateRepository;
import com.calendar.fiserv.calendar.repositories.StateRepository;

@Service
public class ExcelService {

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

	@Autowired
	private HolliDayDateService holliDayDateService;

	@Transactional
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

			if ((long) row.getCell(0).getNumericCellValue() > 31)
				continue;

			holliDayDate.setDay((long) row.getCell(0).getNumericCellValue());

			if (row.getCell(1) == null || (long) row.getCell(1).getNumericCellValue() > 12)
				continue;

			holliDayDate.setMonth((long) row.getCell(1).getNumericCellValue());

			Long year = returnYearActual();

			if (row.getCell(2) != null) {
				if ((long) row.getCell(2).getNumericCellValue() < year)
					continue;

				holliDayDate.setYear((long) row.getCell(2).getNumericCellValue());
			}

			holliDayDate.setActive(validateChar(row.getCell(3).getNumericCellValue()));

			holliDay.setName(row.getCell(4).getStringCellValue().toUpperCase());
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
			if (state != null || stateRecovered != null)
				cityRecovered = cityRepository.findByName(city.getName());

			if (cityRecovered != null || city == null) {
				city = null;
			} else {
				city.setCountry(country == null ? countryRecovered : country);
				city.setState(state == null ? stateRecovered : state);
				city = cityRepository.save(city);
			}

			holliDay.setCreationDate(LocalDateTime.now());

			EHolliDay holliDayRecovered = holliDayRepository.findByName(holliDay.getName());

			if (holliDayRecovered != null) {
				holliDay = null;
			} else {
				holliDay = holliDayRepository.save(holliDay);
			}

			holliDayDate.setHolliday(holliDay == null ? holliDayRecovered : holliDay);

			if (city != null || cityRecovered != null)
				holliDayDate.setCity(city == null ? cityRecovered : city);

			holliDayDate.setCountry(country == null ? countryRecovered : country);

			if (state != null || stateRecovered != null)
				holliDayDate.setState(state == null ? stateRecovered : state);

			holliDayDate.setCreationDate(LocalDateTime.now());

			EHolliDayDate dateRecovered = holliDayDateRepository.findByHolliday(
					country == null ? countryRecovered.getId() : country.getId(),
					holliDay == null ? holliDayRecovered.getId() : holliDay.getId(), holliDayDate.getDay(),
					holliDayDate.getMonth());

			if (dateRecovered == null)
				holliDayDateRepository.insert(holliDayDate);
		}
	}

	public char validateChar(double num) {
		return num == 1 ? '1' : '0';
	}

	public byte[] exportHolliDay() throws IOException {
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet ws = wb.createSheet("Feriados");

		List<HolliDayDateDTO> holliDayDate = holliDayDateService.findAll();

		int headerCount = 0;
		Row header = ws.createRow(0);

		header.createCell(headerCount++).setCellValue("Tipo Feriado");
		header.createCell(headerCount++).setCellValue("Data do feriado");
		header.createCell(headerCount++).setCellValue("Nome do feriado");
		header.createCell(headerCount++).setCellValue("Região");

		ws.setAutoFilter(new CellRangeAddress(0, 0, 0, headerCount - 1));
		ws.createFreezePane(0, 1);

		int rownum = 1;

		for (HolliDayDateDTO h : holliDayDate) {

			Row row = ws.createRow(rownum++);
			int cellnum = 0;

			Cell cell = row.createCell(cellnum++);

			if (h.getCity() != null && h.getCity().getName() != null) {
				cell.setCellValue("Munincipal");
			} else if (h.getState() != null && h.getState().getName() != null) {
				cell.setCellValue("Estadual");
			} else if (h.getCountry() != null && h.getCountry().getName() != null) {
				cell.setCellValue("Nacional");
			}

			cell = row.createCell(cellnum++);
			Long year = h.getYear() == null ? returnYearActual() : h.getYear();
			cell.setCellValue("" + h.getDay() + "/" + h.getMonth() + "/" + year);

			cell = row.createCell(cellnum++);
			cell.setCellValue(h.getHolliday().getName());

			cell = row.createCell(cellnum++);

			if (h.getCity() != null && h.getCity().getName() != null) {
				cell.setCellValue(h.getCity().getName());
			} else if (h.getState() != null && h.getState().getName() != null) {
				cell.setCellValue(h.getState().getName());
			} else if (h.getCountry() != null && h.getCountry().getName() != null) {
				cell.setCellValue(h.getCountry().getName());
			}
		}

		ByteArrayOutputStream out = new ByteArrayOutputStream();
		wb.write(out);
		byte[] report = out.toByteArray();
		out.close();
		return report;

	}

	private Long returnYearActual() {
		return (long) Calendar.getInstance().get(Calendar.YEAR);
	}
}
