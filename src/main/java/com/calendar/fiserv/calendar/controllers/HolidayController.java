package com.calendar.fiserv.calendar.controllers;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import com.calendar.fiserv.calendar.controllers.dto.HolidayDTO;
import com.calendar.fiserv.calendar.controllers.exception.InvalidRowException;
import com.calendar.fiserv.calendar.controllers.form.HolidayForm;
import com.calendar.fiserv.calendar.services.HolidayService;

@RestController
@RequestMapping("/holiday")
public class HolidayController {

	@Autowired
	private HolidayService holidayService;

	@PostMapping
	public ResponseEntity<HolidayDTO> create(@RequestBody HolidayForm form, UriComponentsBuilder uriBuilder) {
		HolidayDTO holiday = holidayService.create(form);
		URI uri = uriBuilder.path("/holiday/{id}").buildAndExpand(holiday.getId()).toUri();
		return ResponseEntity.created(uri).body(holiday);
	}

	@GetMapping
	public ResponseEntity<List<HolidayDTO>> findAll() {
		List<HolidayDTO> holidays = holidayService.findAllHolidays();
		return ResponseEntity.ok(holidays);
	}

	@GetMapping("/{id}")
	public ResponseEntity<HolidayDTO> read(@PathVariable Long id) {
		Optional<HolidayDTO> optional = holidayService.read(id);
		if (optional.isPresent()) {
			return ResponseEntity.ok(optional.get());
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		holidayService.delete(id);
		return ResponseEntity.ok().build();
	}

	@PutMapping("/{id}")
	public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody HolidayForm form) {
		holidayService.update(id, form);
		return ResponseEntity.ok().build();
	}

	@PostMapping("/file")
	public ResponseEntity<Void> createFromXlsFile(@RequestParam("file") MultipartFile file)
			throws InvalidRowException, IOException {
		holidayService.createFromXlsFile(file);
		return ResponseEntity.ok().build();
	}

	@GetMapping("/file")
	public ResponseEntity<byte[]> exportToXlsFile() throws IOException {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Content-Disposition", "attachment;filename=\"holidays.xlsx\"");
		httpHeaders.add("Content-Type", "multipart/form-data");
		
		byte[] file = holidayService.readAllToXls();

		return ResponseEntity.ok().headers(httpHeaders).body(file);
	}

}
