package com.calendar.fiserv.calendar.services.xlsvalidation;

import org.apache.poi.ss.usermodel.Cell;

import com.calendar.fiserv.calendar.services.ExcelService;

public class ValidatorBuilder {

	public static Validator build(int columnIndex, Cell cell) {

		Validator validator;

		switch (columnIndex) {
		case ExcelService.COLUNA_DIA:
			validator = () -> {
				return validDay(cell);
			};
			break;

		case ExcelService.COLUNA_MES:
			validator = () -> {
				return validMonth(cell);
			};
			break;

		case ExcelService.COLUNA_ANO:
			validator = () -> {
				return validYear(cell);
			};
			break;

		case ExcelService.COLUNA_NOME_FERIADO:
			validator = () -> {
				return validHolidayName(cell);
			};
			break;

		case ExcelService.COLUNA_NOME_CIDADE:
			validator = () -> {
				return validCityName(cell);
			};
			break;

		case ExcelService.COLUNA_NOME_ESTADO:
			validator = () -> {
				return validStateName(cell);
			};
			break;

		case ExcelService.COLUNA_CODIGO_ESTADO:
			validator = () -> {
				return validStateCode(cell);
			};
			break;

		case ExcelService.COLUNA_NOME_PAIS:
			validator = () -> {
				return validCountryName(cell);
			};
			break;

		case ExcelService.COLUNA_CODIGO_PAIS:
			validator = () -> {
				return validCountryCode(cell);
			};
			break;

		default:
			validator = () -> {
				return new ValidatedCell();
			};
			break;
		}

		return validator;

	}

	protected static ValidatedCell validCountryCode(Cell cell) throws Exception {

		if (cell == null) {
			return new ValidatedCell("Empty country code");
		}

		String state = cell.getStringCellValue();
		if (state == null || state.trim().isEmpty()) {
			return new ValidatedCell("Empty country code");
		}

		if (state.trim().length() != 2 && state.trim().length() != 3) {
			return new ValidatedCell("Invalid country code");
		}

		return new ValidatedCell();

	}

	protected static ValidatedCell validCountryName(Cell cell) throws Exception {

		if (cell == null) {
			return new ValidatedCell("Empty country name");
		}

		String countryName = cell.getStringCellValue();
		if (countryName == null || countryName.trim().isEmpty()) {
			return new ValidatedCell("Empty country name");
		}

		return new ValidatedCell();

	}

	protected static ValidatedCell validStateCode(Cell cell) throws Exception {
		return new ValidatedCell();
	}

	protected static ValidatedCell validStateName(Cell cell) throws Exception {
		return new ValidatedCell();
	}

	protected static ValidatedCell validCityName(Cell cell) throws Exception {
		return new ValidatedCell();
	}

	protected static ValidatedCell validHolidayName(Cell cell) throws Exception {

		if (cell == null) {
			return new ValidatedCell("Empty holiday name");
		}

		String holidayName = cell.getStringCellValue();
		if (holidayName == null || holidayName.trim().isEmpty()) {
			return new ValidatedCell("Empty holiday name");
		}

		return new ValidatedCell();

	}

	protected static ValidatedCell validYear(Cell cell) throws Exception {

		if (cell == null) {
			return new ValidatedCell();
		}

		cell.getNumericCellValue();

		return new ValidatedCell();
	}

	protected static ValidatedCell validMonth(Cell cell) throws Exception {

		if (cell == null) {
			return new ValidatedCell("Empty month");
		}

		double monthNumeric = cell.getNumericCellValue();

		if (((int) monthNumeric) > 12 || ((int) monthNumeric) < 1) {
			return new ValidatedCell("Invalid month");
		}

		return new ValidatedCell();
	}

	protected static ValidatedCell validDay(Cell cell) throws Exception {

		if (cell == null) {
			return new ValidatedCell("Empty day");
		}

		int dayNumeric = (int) cell.getNumericCellValue();
		if (dayNumeric < 0 || dayNumeric > 31) {
			return new ValidatedCell("Invalid day");
		}

		return new ValidatedCell();
	}

}
