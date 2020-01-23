package com.calendar.fiserv.calendar.services;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.calendar.fiserv.calendar.domain.EHolliDay;
import com.calendar.fiserv.calendar.domain.dto.HolliDayDTO;
import com.calendar.fiserv.calendar.repositories.HolliDayRepository;

@Service
public class HolliDayService {

	@Autowired
	private HolliDayRepository holliDayRepository;

	public EHolliDay holliDayFromDTO(HolliDayDTO dto) {

		EHolliDay holliDay = holliDayRepository.findByName(dto.getName().toUpperCase());

		if (holliDay == null) {
			holliDay = new EHolliDay(null, dto.getName().toUpperCase(), dto.getActive(), LocalDateTime.now(), null);
			holliDay = holliDayRepository.save(holliDay);
		}
		return holliDay;
	}
}
