package com.calendar.fiserv.calendar.services;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.calendar.fiserv.calendar.domain.ECity;
import com.calendar.fiserv.calendar.domain.ECountry;
import com.calendar.fiserv.calendar.domain.EHolliDay;
import com.calendar.fiserv.calendar.domain.EHolliDayDate;
import com.calendar.fiserv.calendar.domain.EState;
import com.calendar.fiserv.calendar.domain.dto.HolliDayDateDTO;
import com.calendar.fiserv.calendar.repositories.CityRepository;
import com.calendar.fiserv.calendar.repositories.CountryRepository;
import com.calendar.fiserv.calendar.repositories.HolliDayRepository;
import com.calendar.fiserv.calendar.repositories.HollidayDateRepository;
import com.calendar.fiserv.calendar.repositories.StateRepository;

@Service
public class HolliDayDateService {

	@Autowired
	private HollidayDateRepository holliDayDateRepository;

	@Autowired
	private HolliDayRepository holliDayRepository;

	@Autowired
	private CountryRepository countryRepository;

	@Autowired
	private StateRepository stateRepository;

	@Autowired
	private CityRepository cityRepository;

	@Transactional
	public EHolliDayDate fromDTO(HolliDayDateDTO dto) {

		ECountry country = new ECountry(null, dto.getCountry().getName().toUpperCase(),
				dto.getCountry().getCode().toUpperCase(), dto.getCountry().getActive(), LocalDateTime.now(), null,
				dto.getCountry().getHasState(), null, null, null);

		EHolliDay holliDay = new EHolliDay(null, dto.getHolliday().getName(), dto.getHolliday().getActive(),
				LocalDateTime.now(), null);

		EState state;
		if (dto.getState() != null) {
			state = new EState(null, dto.getState().getName().toUpperCase(), dto.getState().getCode(),
					dto.getState().getActive(), LocalDateTime.now(), null, country, null, null);
		} else {
			state = null;
		}

		ECity city;
		if (dto.getCity() != null) {
			city = new ECity(null, dto.getCity().getName(), dto.getCity().getActive(), LocalDateTime.now(), null, state,
					country, null);
		} else {
			city = null;
		}

		EHolliDayDate holliDayDate = new EHolliDayDate(null, dto.getDay(), dto.getMonth(), dto.getYear(),
				dto.getActive(), LocalDateTime.now(), null, holliDay, country, state, city);

		ECountry countryRecovered = countryRepository.findByName(country.getName());

		if (countryRecovered != null) {
			country = null;
		} else {
			country = countryRepository.save(country);
		}

		EState stateRecovered = null;
		if (state != null)
			stateRecovered = stateRepository.findByName(state.getName());

		if (state != null && stateRecovered == null) {
			state.setCountry(country == null ? countryRecovered : country);
			state.setCitys(Arrays.asList(city));
			state.setHollyDayDate(Arrays.asList(holliDayDate));
			state = stateRepository.save(state);

		}

		ECity cityRecovered = null;
		if (city != null)
			cityRecovered = cityRepository.findByName(city.getName());

		if (city != null && cityRecovered == null) {
			city.setHollyDayDate(Arrays.asList(holliDayDate));
			if (state == null && stateRecovered == null)
				return null;

			city.setState(state == null ? stateRecovered : state);
			city.setCountry(country == null ? countryRecovered : country);
			city = cityRepository.save(city);
		}

		holliDay = holliDayRepository.save(holliDay);

		holliDayDate.setCountry(country == null ? countryRecovered : country);

		if (state == null && stateRecovered == null) {
			holliDayDate.setState(null);
		} else {
			holliDayDate.setState(state == null ? stateRecovered : state);
		}

		if (city == null && cityRecovered == null) {
			holliDayDate.setCity(null);
		} else {
			holliDayDate.setCity(city == null ? cityRecovered : city);
		}

		holliDayDate.setHolliday(holliDay);
		holliDayDate.setId("CDHJ");

		return holliDayDateRepository.save(holliDayDate);
	}

	public EHolliDayDate insert(EHolliDayDate holli) {
		return holliDayDateRepository.save(holli);
	}

	public List<EHolliDayDate> findAll() {
		return holliDayDateRepository.findAll();
	}

}
