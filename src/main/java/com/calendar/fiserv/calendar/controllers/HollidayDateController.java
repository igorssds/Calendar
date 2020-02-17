package com.calendar.fiserv.calendar.controllers;

import java.io.IOException;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.calendar.fiserv.calendar.controllers.exception.InvalidRowException;
import com.calendar.fiserv.calendar.domain.ECity;
import com.calendar.fiserv.calendar.domain.ECountry;
import com.calendar.fiserv.calendar.domain.EHolliDay;
import com.calendar.fiserv.calendar.domain.EState;
import com.calendar.fiserv.calendar.domain.dto.HolliDayDateDTO;
import com.calendar.fiserv.calendar.services.CityService;
import com.calendar.fiserv.calendar.services.CountryService;
import com.calendar.fiserv.calendar.services.ExcelService;
import com.calendar.fiserv.calendar.services.HolliDayDateService;
import com.calendar.fiserv.calendar.services.HolliDayService;
import com.calendar.fiserv.calendar.services.StateService;
import com.calendar.fiserv.calendar.services.dto.HolliDayDateRemoveDTO;
import com.calendar.fiserv.calendar.services.dto.HolliDayDateUpdateDTO;

@RestController
@RequestMapping(value = "/holliday-date", produces = MediaType.APPLICATION_JSON_VALUE)
public class HollidayDateController {

	@Autowired
	private HolliDayDateService holliDayDateService;

	@Autowired
	private CountryService countryService;

	@Autowired
	private StateService stateService;

	@Autowired
	private CityService cityService;

	@Autowired
	private HolliDayService holliDayService;

	@Autowired
	private ExcelService util;

	@PostMapping
	public ResponseEntity<Void> insert(@Valid @RequestBody HolliDayDateDTO dto) {

		ECountry country = countryService.countryFromDTO(dto.getCountry());
		EState state = stateService.stateFromDTO(dto.getState(), country);
		ECity city = cityService.cityFromDTO(dto.getCity(), state, country);
		EHolliDay holliDay = holliDayService.holliDayFromDTO(dto.getHolliday());

		holliDayDateService.fromDTO(dto, country, state, city, holliDay);

		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	/**
	 * The service inserts holiday dates described by the XLS file, each holiday per
	 * row. If the file contains some invalid row, such as a holiday with invalid
	 * date or without name, the service inserts the valid holidays, and returns a
	 * HTTP 206 (Partial Content), with a XLS file body describing the validation
	 * errors. Otherwise, it will return a HTTP 200 OK without body.
	 * 
	 * @param file
	 * @return
	 * @throws IOException
	 * @throws InvalidRowException
	 */
	@PostMapping("/file")
	public ResponseEntity<?> insertToFile(@RequestParam("file") MultipartFile file)
			throws IOException, InvalidRowException {
		util.readToHolliDays(file.getInputStream());
		return ResponseEntity.ok().build();
	}

	@GetMapping
	public List<HolliDayDateDTO> findAll() {
		return holliDayDateService.findAll();
	}

	@GetMapping("/export")
	public ResponseEntity<byte[]> download() throws IOException {
		byte[] arquivo = util.exportHolliDay();

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Content-Disposition", "attachment;filename= \"exportHolliDays.xlsx\"");
		httpHeaders.add("Content-Type", "multipart/form-data");

		return ResponseEntity.ok().headers(httpHeaders).body(arquivo);
	}

	@PostMapping("/remove")
	public ResponseEntity<Void> remove(@RequestBody HolliDayDateRemoveDTO dto) {
		holliDayDateService.remove(dto);
		return ResponseEntity.ok().build();
	}

	@PostMapping("/update")
	public ResponseEntity<Void> update(@Valid @RequestBody HolliDayDateUpdateDTO dto) {
		holliDayService.update(dto.getHolliDay());
		holliDayDateService.update(dto);
		return ResponseEntity.ok().build();
	}
}
