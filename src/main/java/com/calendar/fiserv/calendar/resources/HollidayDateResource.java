package com.calendar.fiserv.calendar.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.calendar.fiserv.calendar.domain.HolliDayDate;
import com.calendar.fiserv.calendar.domain.dto.HolliDayDateDTO;
import com.calendar.fiserv.calendar.services.HolliDayDateService;

@RestController
@RequestMapping("/holliday")
public class HollidayDateResource {
	
	@Autowired
	private HolliDayDateService holliDayDayeService;
	
	@PostMapping()
	public ResponseEntity<HolliDayDate> insert(@RequestBody HolliDayDateDTO dto){
		
		HolliDayDate holli = holliDayDayeService.fromDTO(dto);
		
		HolliDayDate holliDayDate = holliDayDayeService.insert(holli);
		
		return null;
	}
}
