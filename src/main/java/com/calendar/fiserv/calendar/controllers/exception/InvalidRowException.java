package com.calendar.fiserv.calendar.controllers.exception;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.calendar.fiserv.calendar.services.ExcelService;
import com.calendar.fiserv.calendar.services.xlsvalidation.InvalidColumn;
import com.calendar.fiserv.calendar.services.xlsvalidation.InvalidRow;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@Slf4j
public class InvalidRowException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<InvalidRow> invalidRows;
	private byte[] xlsFileWithErrors;

	public InvalidRowException(List<InvalidRow> invalidRows) {
		this.invalidRows = invalidRows;
		try {
			this.xlsFileWithErrors = createFile(invalidRows);
		} catch (Exception ex) {
			log.error(ex.getMessage());
			this.xlsFileWithErrors = null;
		}
	}

	private byte[] createFile(List<InvalidRow> invalidRows) throws IOException {
		XSSFWorkbook wb = new XSSFWorkbook();
		XSSFSheet ws = wb.createSheet("INVALID_HOLIDAY_INPUT");

		createHeader(ws);

		int rowCount = 1;
		for (InvalidRow invalidRow : invalidRows) {
			Row row = ws.createRow(rowCount++);
			int totalCells = 9;
			for (int i = 0; i < totalCells; i++) {
				switch (i) {
				case ExcelService.COLUNA_DIA:
					if (invalidRow.getRow().getCell(i) != null) {
						row.createCell(i).setCellValue(invalidRow.getRow().getCell(i).getNumericCellValue());
					} else {
						row.createCell(i);
					}
					break;
				case ExcelService.COLUNA_MES:
					if (invalidRow.getRow().getCell(i) != null) {
						row.createCell(i).setCellValue(invalidRow.getRow().getCell(i).getNumericCellValue());
					} else {
						row.createCell(i);
					}
					break;
				case ExcelService.COLUNA_ANO:
					if (invalidRow.getRow().getCell(i) != null) {
						row.createCell(i).setCellValue(invalidRow.getRow().getCell(i).getNumericCellValue());
					} else {
						row.createCell(i);
					}
					break;
				case ExcelService.COLUNA_NOME_FERIADO:
					if (invalidRow.getRow().getCell(i) != null) {
						row.createCell(i).setCellValue(invalidRow.getRow().getCell(i).getStringCellValue());
					} else {
						row.createCell(i);
					}
					break;
				case ExcelService.COLUNA_NOME_CIDADE:
					if (invalidRow.getRow().getCell(i) != null) {
						row.createCell(i).setCellValue(invalidRow.getRow().getCell(i).getStringCellValue());
					} else {
						row.createCell(i);
					}
					break;
				case ExcelService.COLUNA_NOME_ESTADO:
					if (invalidRow.getRow().getCell(i) != null) {
						row.createCell(i).setCellValue(invalidRow.getRow().getCell(i).getStringCellValue());
					} else {
						row.createCell(i);
					}
					break;
				case ExcelService.COLUNA_CODIGO_ESTADO:
					if (invalidRow.getRow().getCell(i) != null) {
						row.createCell(i).setCellValue(invalidRow.getRow().getCell(i).getStringCellValue());
					} else {
						row.createCell(i);
					}
					break;
				case ExcelService.COLUNA_NOME_PAIS:
					if (invalidRow.getRow().getCell(i) != null) {
						row.createCell(i).setCellValue(invalidRow.getRow().getCell(i).getStringCellValue());
					} else {
						row.createCell(i);
					}
					break;
				case ExcelService.COLUNA_CODIGO_PAIS:
					if (invalidRow.getRow().getCell(i) != null) {
						row.createCell(i).setCellValue(invalidRow.getRow().getCell(i).getStringCellValue());
					} else {
						row.createCell(i);
					}
					break;
				}
			}

			row.createCell(totalCells).setCellValue(buildStringInvalidCauses(invalidRow.getColumnNumber()));
		}

		return writeWorkbook(wb);

	}

	private void createHeader(XSSFSheet ws) {
		Row header = ws.createRow(0);

		int headerCount = 0;

		header.createCell(headerCount++).setCellValue("Day");
		header.createCell(headerCount++).setCellValue("Month");
		header.createCell(headerCount++).setCellValue("Year");
		header.createCell(headerCount++).setCellValue("Holiday name");
		header.createCell(headerCount++).setCellValue("City name");
		header.createCell(headerCount++).setCellValue("State name");
		header.createCell(headerCount++).setCellValue("State code");
		header.createCell(headerCount++).setCellValue("Country name");
		header.createCell(headerCount++).setCellValue("Country code");

		ws.setAutoFilter(new CellRangeAddress(0, 0, 0, headerCount - 1));
		ws.createFreezePane(0, 1);
	}

	private byte[] writeWorkbook(XSSFWorkbook wb) throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		wb.write(out);
		byte[] report = out.toByteArray();
		out.close();
		return report;
	}

	private String buildStringInvalidCauses(List<InvalidColumn> invalidColumns) {
		List<String> causes = invalidColumns.stream().map(column -> column.getCause()).collect(Collectors.toList());
		return String.join("; ", causes);
	}

}
