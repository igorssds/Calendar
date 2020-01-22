package com.calendar.fiserv.calendar.services;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.calendar.fiserv.calendar.domain.ECountry;
import com.calendar.fiserv.calendar.domain.dto.CountryDTO;
import com.calendar.fiserv.calendar.repositories.CountryRepository;

@Service
public class CountryService {

	@Autowired
	private CountryRepository countryRepository;

	public ECountry countryFromDTO(CountryDTO dto) {
		ECountry country = countryRepository.findByName(dto.getName().toUpperCase());

		if (country == null) {
			country = new ECountry(null, dto.getName().toUpperCase(), dto.getCode().toUpperCase(), dto.getActive(),
					LocalDateTime.now(), null, dto.getHasState(), null, null, null);
			country = countryRepository.save(country);
		}
		return country;
	}
}
