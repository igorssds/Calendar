package com.calendar.fiserv.calendar.controllers;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.calendar.fiserv.calendar.controllers.dto.WorkdayDTO;
import com.calendar.fiserv.calendar.services.WorkdayService;

@RestController
@RequestMapping("/workday")
public class WorkdayController {

	@Autowired
	private WorkdayService workdayService;

	@GetMapping("/next")
	public ResponseEntity<WorkdayDTO> nextWorkday(
			@RequestParam(name = "calculation-base-date", required = true) @DateTimeFormat(iso = ISO.DATE) LocalDate baseDate,
			@RequestParam(name = "sla-period-in-days", required = true) int sla,
			@RequestParam(name = "city-name", required = false) String cityName,
			@RequestParam(name = "state-code", required = false) String stateCode,
			@RequestParam(name = "country-code", required = true) String countryCode) {

		WorkdayDTO workday = workdayService.getNextWorkday(baseDate, sla, cityName, stateCode, countryCode);
		return ResponseEntity.ok(workday);
	}

}
