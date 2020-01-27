package com.calendar.fiserv.calendar.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.calendar.fiserv.calendar.domain.dto.HolliDayDTO;
import com.calendar.fiserv.calendar.services.HolliDayService;

@RestController
@RequestMapping("/holliday")
public class HolliDayController {

	@Autowired
	private HolliDayService holliDayService;
	
	@PostMapping("/{id}")
	public ResponseEntity<?> update(@PathVariable Long id ,@RequestBody HolliDayDTO dto){
		dto.setId(id);
		holliDayService.update(dto);
		return ResponseEntity.noContent().build();
	}
}
