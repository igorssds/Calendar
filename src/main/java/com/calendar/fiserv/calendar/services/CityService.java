package com.calendar.fiserv.calendar.services;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.calendar.fiserv.calendar.domain.ECity;
import com.calendar.fiserv.calendar.domain.ECountry;
import com.calendar.fiserv.calendar.domain.EState;
import com.calendar.fiserv.calendar.domain.dto.CityDTO;
import com.calendar.fiserv.calendar.repositories.CityRepository;

@Service
public class CityService {

	@Autowired
	private CityRepository cityRepository;

	public ECity cityFromDTO(CityDTO dto, EState state, ECountry country) {

		if (dto == null)
			return new ECity();

		ECity city = cityRepository.findByName(dto.getName().toUpperCase());

		if (city == null) {
			city = new ECity(null, dto.getName().toUpperCase(), dto.getActive(), LocalDateTime.now(), null, state,
					country);

			city = cityRepository.save(city);
		}
		return city;
	}
}
