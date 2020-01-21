package com.calendar.fiserv.calendar.controllers;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.calendar.fiserv.calendar.domain.EHolliDayDate;
import com.calendar.fiserv.calendar.domain.dto.HolliDayDateDTO;
import com.calendar.fiserv.calendar.services.ExcelUtil;
import com.calendar.fiserv.calendar.services.HolliDayDateService;

@RestController
@RequestMapping("/holliday")
public class HollidayDateController {

	@Autowired
	private HolliDayDateService holliDayDateService;

	@Autowired
	private ExcelUtil util;

	@PostMapping("/insert")
	public ResponseEntity<Void> insert(@RequestBody  HolliDayDateDTO dto) {
		holliDayDateService.fromDTO(dto);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@PostMapping("/insert/file")
	public ResponseEntity<Void> insertToFile(@RequestParam("file") MultipartFile file) throws IOException {
		util.readToHolliDays(file.getInputStream());
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@GetMapping
	public ResponseEntity<List<EHolliDayDate>> findAll(){
		return ResponseEntity.ok().body(holliDayDateService.findAll());
	}
}
