package com.calendar.fiserv.calendar.services;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
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

import com.calendar.fiserv.calendar.controllers.exception.InvalidRowException;
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
import com.calendar.fiserv.calendar.services.xlsvalidation.InvalidColumn;
import com.calendar.fiserv.calendar.services.xlsvalidation.InvalidRow;
import com.calendar.fiserv.calendar.services.xlsvalidation.ValidatedCell;
import com.calendar.fiserv.calendar.services.xlsvalidation.ValidatorBuilder;

@Service
public class ExcelService {

	public static final int COLUNA_DIA = 0;
	public static final int COLUNA_MES = 1;
	public static final int COLUNA_ANO = 2;
	public static final int COLUNA_NOME_FERIADO = 3;
	public static final int COLUNA_NOME_CIDADE = 4;
	public static final int COLUNA_NOME_ESTADO = 5;
	public static final int COLUNA_CODIGO_ESTADO = 6;
	public static final int COLUNA_NOME_PAIS = 7;
	public static final int COLUNA_CODIGO_PAIS = 8;

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
	public void readToHolliDays_OLD(InputStream stream) throws IOException {

		XSSFWorkbook wb = new XSSFWorkbook(stream);
		XSSFSheet ws = wb.getSheetAt(0);

		for (int i = 1; i < ws.getPhysicalNumberOfRows(); i++) {
			EHolliDayDate holliDayDate = new EHolliDayDate();
			Row row = ws.getRow(i);

			if (row.getCell(COLUNA_DIA) == null)
				continue;

			if ((int) row.getCell(0).getNumericCellValue() > 31)
				continue;

			holliDayDate.setDay((int) row.getCell(0).getNumericCellValue());

			if (row.getCell(1) == null || (int) row.getCell(1).getNumericCellValue() > 12)
				continue;

			holliDayDate.setMonth((int) row.getCell(1).getNumericCellValue());

			int year = returnYearActual();

			if (row.getCell(2) != null) {
				if ((int) row.getCell(2).getNumericCellValue() < year)
					continue;

				holliDayDate.setYear((int) row.getCell(2).getNumericCellValue());
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

	@Transactional
	public void readToHolliDays(InputStream stream) throws IOException, InvalidRowException {
		XSSFWorkbook wb = new XSSFWorkbook(stream);
		XSSFSheet ws = wb.getSheetAt(0);
		List<InvalidRow> invalidRows = new ArrayList<>();

		for (int i = 1; i < ws.getPhysicalNumberOfRows(); i++) {
			Row row = ws.getRow(i);
			List<InvalidColumn> invalidColumns = validRow(row);
			if (invalidColumns.isEmpty()) {
				persistRow(row);
			} else {
				invalidRows.add(new InvalidRow(invalidColumns, row));
			}
		}

		if (!invalidRows.isEmpty()) {
			throw new InvalidRowException(invalidRows);
		}

	}

	private void persistRow(Row row) {

		EHolliDayDate holliDayDate = new EHolliDayDate();

		holliDayDate.setDay((int) row.getCell(0).getNumericCellValue());
		holliDayDate.setMonth((int) row.getCell(1).getNumericCellValue());

		int year = returnYearActual();

		if (row.getCell(2) != null) {
			holliDayDate.setYear((int) row.getCell(2).getNumericCellValue());
		} else {
			holliDayDate.setYear(year); // ?
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

			String plainTextCity = StringNormalizer
					.toPlainTextUpperCase(row.getCell(COLUNA_NOME_CIDADE).getStringCellValue());
			city = cityRepository.findByName(plainTextCity);

			if (city != null)
				isCity = true;

			if (city == null) {
				city = new ECity();
				city.setName(plainTextCity);
				city.setActive('1');
				city.setCreationDate(LocalDateTime.now());
			}
		}

		boolean isState = false;
		EState state = null;
		if (row.getCell(COLUNA_CODIGO_ESTADO) != null && row.getCell(COLUNA_NOME_ESTADO) != null) {

			String plainTextStateCode = StringNormalizer
					.toPlainTextUpperCase(row.getCell(COLUNA_CODIGO_ESTADO).getStringCellValue());
			state = stateRepository.findByCode(plainTextStateCode);

			if (state != null)
				isState = true;

			if (state == null) {
				state = new EState();
				state.setName(row.getCell(COLUNA_NOME_ESTADO).getStringCellValue());
				state.setActive('1');
				state.setCreationDate(LocalDateTime.now());
				if (row.getCell(6) != null)
					state.setCode(plainTextStateCode);
			}

		}

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

	private List<InvalidColumn> validRow(Row row) {

		List<InvalidColumn> invalidColumns = new ArrayList<>();

		int totalCells = 9;
		for (int i = 0; i < totalCells; i++) {
			ValidatedCell validatedCell;
			try {
				validatedCell = ValidatorBuilder.build(i, row.getCell(i)).valid();
			} catch (Exception ex) {
				validatedCell = new ValidatedCell(ex.getMessage());
			}

			if (!validatedCell.isValid()) {
				invalidColumns.add(new InvalidColumn(i, validatedCell.getCauseOfInvalid()));
			}
		}

		return invalidColumns;

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

	private int returnYearActual() {
		return (int) Calendar.getInstance().get(Calendar.YEAR);
	}
}
