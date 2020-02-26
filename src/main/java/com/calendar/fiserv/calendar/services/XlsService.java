package com.calendar.fiserv.calendar.services;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.calendar.fiserv.calendar.controllers.form.HolidayForm;
import com.calendar.fiserv.calendar.model.EHoliday;
import com.calendar.fiserv.calendar.services.dto.InvalidHoliday;

@Service
public class XlsService {

	private static final int TOTAL_XLS_COLUMNS = XlsColumns.values().length;

	public List<HolidayForm> extractHolidays(MultipartFile file) throws IOException {

		List<HolidayForm> holidays = new ArrayList<>();

		InputStream stream = file.getInputStream();
		XSSFWorkbook wb = new XSSFWorkbook(stream);
		XSSFSheet ws = wb.getSheetAt(0);

		for (int i = 1; i < ws.getPhysicalNumberOfRows(); i++) {
			Row row = ws.getRow(i);
			if (!isEmpty(row)) {
				holidays.add(extractRow(row));
			}
		}

		return holidays;

	}

	private boolean isEmpty(Row row) {
		for (int i = 0; i < row.getPhysicalNumberOfCells(); i++) {
			if (row.getCell(i) != null) {
				return false;
			}
		}
		return true;
	}

	private HolidayForm extractRow(Row row) {
		return HolidayForm.builder()

				.date(extractDate(row))

				.name(extractString(row, XlsColumns.HOLIDAY_NAME_COLUMN))

				.cityName(extractString(row, XlsColumns.CITY_NAME_COLUMN))

				.stateName(extractString(row, XlsColumns.STATE_NAME_COLUMN))

				.stateCode(extractString(row, XlsColumns.STATE_CODE_COLUMN))

				.countryName(extractString(row, XlsColumns.COUNTRY_NAME_COLUMN))

				.countryCode(extractString(row, XlsColumns.COUNTRY_CODE_COLUMN))

				.active(true)

				.build();

	}

	private String extractString(Row row, XlsColumns column) {
		if (row.getCell(column.getIndex()) != null) {
			return row.getCell(column.getIndex()).getStringCellValue();
		}

		return null;
	}

	private LocalDate extractDate(Row row) {

		int year = LocalDate.now().getYear();
		if (row.getCell(XlsColumns.YEAR_COLUMN.getIndex()) != null) {
			try {
				year = (int) row.getCell(XlsColumns.YEAR_COLUMN.getIndex()).getNumericCellValue();
			} catch (Exception ex) {
				year = -1;
			}
		}

		try {
			return LocalDate.of(year, (int) row.getCell(XlsColumns.MONTH_COLUMN.getIndex()).getNumericCellValue(),
					(int) row.getCell(XlsColumns.DAY_COLUMN.getIndex()).getNumericCellValue());
		} catch (Exception ex) {
			return null;
		}
	}

	public byte[] convertToXls(List<EHoliday> holidays) throws IOException {

		XSSFWorkbook wb = new XSSFWorkbook();
		XSSFSheet ws = wb.createSheet("Holidays");

		populateSheet(ws, holidays);

		ws.setAutoFilter(new CellRangeAddress(0, 0, 0, TOTAL_XLS_COLUMNS - 1));
		ws.createFreezePane(0, 1);

		return writeFile(wb);

	}

	private byte[] writeFile(XSSFWorkbook wb) throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		wb.write(out);
		byte[] report = out.toByteArray();
		out.close();
		return report;
	}

	private void populateSheet(XSSFSheet ws, List<EHoliday> holidays) {
		int row = 0;
		populateHeader(ws.createRow(row++));
		for (EHoliday holiday : holidays) {
			populateRow(ws.createRow(row++), holiday);
		}
	}

	private void populateRow(Row row, EHoliday holiday) {

		row.createCell(XlsColumns.DAY_COLUMN.getIndex()).setCellValue(holiday.getDate() != null ? holiday.getDate().getDayOfMonth() : 0);
		row.createCell(XlsColumns.MONTH_COLUMN.getIndex()).setCellValue(holiday.getDate() != null ? holiday.getDate().getMonthValue() : 0);
		row.createCell(XlsColumns.YEAR_COLUMN.getIndex()).setCellValue(holiday.getDate() != null ? holiday.getDate().getYear() : 0);
		row.createCell(XlsColumns.HOLIDAY_NAME_COLUMN.getIndex()).setCellValue(holiday.getName());
		row.createCell(XlsColumns.CITY_NAME_COLUMN.getIndex()).setCellValue(holiday.getCityName());
		row.createCell(XlsColumns.STATE_NAME_COLUMN.getIndex()).setCellValue(holiday.getStateName());
		row.createCell(XlsColumns.STATE_CODE_COLUMN.getIndex()).setCellValue(holiday.getStateCode());
		row.createCell(XlsColumns.COUNTRY_NAME_COLUMN.getIndex()).setCellValue(holiday.getCountryName());
		row.createCell(XlsColumns.COUNTRY_CODE_COLUMN.getIndex()).setCellValue(holiday.getCountryCode());

	}

	private void populateHeader(Row row) {

		row.createCell(XlsColumns.DAY_COLUMN.getIndex()).setCellValue(XlsColumns.DAY_COLUMN.getColumnHeader());
		row.createCell(XlsColumns.MONTH_COLUMN.getIndex()).setCellValue(XlsColumns.MONTH_COLUMN.getColumnHeader());
		row.createCell(XlsColumns.YEAR_COLUMN.getIndex()).setCellValue(XlsColumns.YEAR_COLUMN.getColumnHeader());
		row.createCell(XlsColumns.HOLIDAY_NAME_COLUMN.getIndex())
				.setCellValue(XlsColumns.HOLIDAY_NAME_COLUMN.getColumnHeader());
		row.createCell(XlsColumns.CITY_NAME_COLUMN.getIndex())
				.setCellValue(XlsColumns.CITY_NAME_COLUMN.getColumnHeader());
		row.createCell(XlsColumns.STATE_NAME_COLUMN.getIndex())
				.setCellValue(XlsColumns.STATE_NAME_COLUMN.getColumnHeader());
		row.createCell(XlsColumns.STATE_CODE_COLUMN.getIndex())
				.setCellValue(XlsColumns.STATE_CODE_COLUMN.getColumnHeader());
		row.createCell(XlsColumns.COUNTRY_NAME_COLUMN.getIndex())
				.setCellValue(XlsColumns.COUNTRY_NAME_COLUMN.getColumnHeader());
		row.createCell(XlsColumns.COUNTRY_CODE_COLUMN.getIndex())
				.setCellValue(XlsColumns.COUNTRY_CODE_COLUMN.getColumnHeader());

	}

	private enum XlsColumns {

		DAY_COLUMN(0, "Day"), MONTH_COLUMN(1, "Month"), YEAR_COLUMN(2, "Year"), HOLIDAY_NAME_COLUMN(3, "Holiday name"),
		CITY_NAME_COLUMN(4, "City name"), STATE_NAME_COLUMN(5, "State name"), STATE_CODE_COLUMN(6, "State code"),
		COUNTRY_NAME_COLUMN(7, "Country name"), COUNTRY_CODE_COLUMN(8, "Country code");

		private final int index;
		private final String columnHeader;

		XlsColumns(int index, String columnHeader) {
			this.index = index;
			this.columnHeader = columnHeader;
		}

		public int getIndex() {
			return index;
		}

		public String getColumnHeader() {
			return columnHeader;
		}

	}

	public byte[] convertInvalidsToXls(List<InvalidHoliday> invalidHolidays) throws IOException {

		XSSFWorkbook wb = new XSSFWorkbook();
		XSSFSheet ws = wb.createSheet("Holidays");

		populateSheetWithInvalids(ws, invalidHolidays);

		ws.setAutoFilter(new CellRangeAddress(0, 0, 0, TOTAL_XLS_COLUMNS - 1));
		ws.createFreezePane(0, 1);

		return writeFile(wb);

	}

	private void populateSheetWithInvalids(XSSFSheet ws, List<InvalidHoliday> invalidHolidays) {

		int row = 0;
		Row header = ws.createRow(row++);
		populateHeader(header);
		header.createCell(TOTAL_XLS_COLUMNS).setCellValue("Errors");

		for (InvalidHoliday invalidHoliday : invalidHolidays) {
			Row _row = ws.createRow(row++);
			populateRow(_row, invalidHoliday.getHoliday().toHoliday());
			_row.createCell(TOTAL_XLS_COLUMNS).setCellValue(invalidHoliday.getCauses());
		}
	}

}
