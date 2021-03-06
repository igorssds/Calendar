package com.calendar.fiserv.calendar.services;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.List;

import javax.transaction.Transactional;

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

			holliDayDate.setActive('1');

			EHolliDay holliDay = holliDayRepository.findByName(row.getCell(3).getStringCellValue().toUpperCase());

			if (holliDay == null) {
				holliDay = new EHolliDay();
				holliDay.setName(row.getCell(3).getStringCellValue().toUpperCase());
				holliDay.setActive('1');
				holliDay.setCreationDate(LocalDateTime.now());
				holliDay = holliDayRepository.save(holliDay);
			}

			boolean isCity = false;
			ECity city = null;
			if (row.getCell(4) != null) {
				city = cityRepository.findByName(row.getCell(4).getStringCellValue().toUpperCase());

				if (city != null)
					isCity = true;

				if (city == null) {
					city = new ECity();
					city.setName(row.getCell(4).getStringCellValue().toUpperCase());
					city.setActive('1');
					city.setCreationDate(LocalDateTime.now());
				}
			}

			boolean isState = false;
			EState state = null;
			if (row.getCell(5) != null) {
				state = stateRepository.findByName(row.getCell(5).getStringCellValue().toUpperCase());

				if (state != null)
					isState = true;

				if (state == null) {
					state = new EState();
					state.setName(row.getCell(5).getStringCellValue().toUpperCase());
					state.setActive('1');
					state.setCreationDate(LocalDateTime.now());
					if (row.getCell(6) != null)
						state.setCode(row.getCell(6).getStringCellValue());
				}

			}

			if (row.getCell(7) == null)
				continue;

			ECountry country = countryRepository.findByName(row.getCell(7).getStringCellValue().toUpperCase());

			if (country == null) {
				country = new ECountry();
				country.setName(row.getCell(7).getStringCellValue().toUpperCase());
				country.setCode(row.getCell(8).getStringCellValue());
				country.setActive('1');
				country.setHasState('1');
				country.setCreationDate(LocalDateTime.now());
				country = countryRepository.save(country);
			}

			if (state != null && !isState) {
				state.setCountry(country);
				state = stateRepository.save(state);
			}

			if (city != null && !isCity) {
				city.setCountry(country);
				city.setState(state);
				city = cityRepository.save(city);
			}

			holliDayDate.setHolliday(holliDay);
			holliDayDate.setCity(city);
			holliDayDate.setCountry(country);
			holliDayDate.setState(state);
			holliDayDate.setCreationDate(LocalDateTime.now());

			EHolliDayDate dateRecovered = holliDayDateRepository.findByHolliday(country.getId(), holliDay.getId(),
					holliDayDate.getDay(), holliDayDate.getMonth());

			if (dateRecovered == null)
				holliDayDateRepository.insert(holliDayDate);
		}
	}

	public char validateChar(double num) {
		return num == 1 ? '1' : '0';
	}

	public byte[] exportHolliDay() throws IOException {
		
		XSSFWorkbook wb = new XSSFWorkbook();
		XSSFSheet ws = wb.createSheet("Feriados");

		List<HolliDayDateDTO> holliDayDate = holliDayDateService.findAll();

		int headerCount = 0;
		Row header = ws.createRow(0);

		header.createCell(headerCount++).setCellValue("Dia");
		header.createCell(headerCount++).setCellValue("Mês");
		header.createCell(headerCount++).setCellValue("Ano");
		header.createCell(headerCount++).setCellValue("Nome Feriado");
		header.createCell(headerCount++).setCellValue("Nome Cidade");
		header.createCell(headerCount++).setCellValue("Nome Estado");
		header.createCell(headerCount++).setCellValue("Codigo Estado");
		header.createCell(headerCount++).setCellValue("Nome Pais");
		header.createCell(headerCount++).setCellValue("Codigo Pais");

		ws.setAutoFilter(new CellRangeAddress(0, 0, 0, headerCount - 1));
		ws.createFreezePane(0, 1);

		int rownum = 1;

		for (HolliDayDateDTO h : holliDayDate) {

			Row row = ws.createRow(rownum++);
			int cellnum = 0;

			Cell cell = row.createCell(cellnum++);
			cell.setCellValue(h.getDay());

			cell = row.createCell(cellnum++);
			cell.setCellValue(h.getMonth());

			cell = row.createCell(cellnum++);
			cell.setCellValue(h.getYear());

			cell = row.createCell(cellnum++);
			cell.setCellValue(h.getHolliday().getName());

			cell = row.createCell(cellnum++);
			cell.setCellValue(h.getCity().getName());

			cell = row.createCell(cellnum++);
			cell.setCellValue(h.getState().getName());

			cell = row.createCell(cellnum++);
			cell.setCellValue(h.getState().getCode());

			cell = row.createCell(cellnum++);
			cell.setCellValue(h.getCountry().getName());

			cell = row.createCell(cellnum++);
			cell.setCellValue(h.getCountry().getCode());

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
