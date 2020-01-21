package com.calendar.fiserv.calendar.services;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.calendar.fiserv.calendar.domain.City;
import com.calendar.fiserv.calendar.domain.Country;
import com.calendar.fiserv.calendar.domain.HolliDay;
import com.calendar.fiserv.calendar.domain.HolliDayDate;
import com.calendar.fiserv.calendar.domain.HolliDayDate.HollidayDateId;
import com.calendar.fiserv.calendar.domain.State;
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
	public HolliDayDate fromDTO(HolliDayDateDTO dto) {

		Country country = new Country(null, dto.getCountry().getName().toUpperCase(),
				dto.getCountry().getCode().toUpperCase(), dto.getCountry().getActive(), LocalDateTime.now(), null,
				dto.getCountry().getHasState(), null, null, null);

		HolliDay holliDay = new HolliDay(null, dto.getHolliday().getName(), dto.getHolliday().getActive(),
				LocalDateTime.now(), null);

		State state;
		if (dto.getState() != null) {
			state = new State(null, dto.getState().getName().toUpperCase(), dto.getState().getCode(),
					dto.getState().getActive(), LocalDateTime.now(), null, country, null, null);
		} else {
			state = null;
		}

		City city;
		if (dto.getCity() != null) {
			city = new City(null, dto.getCity().getName(), dto.getCity().getActive(), LocalDateTime.now(), null, state,
					country, null);
		} else {
			city = null;
		}

		HolliDayDate holliDayDate = new HolliDayDate(null, dto.getActive(), LocalDateTime.now(), null, holliDay,
				country, state, city);

		Country countryRecovered = countryRepository.findByName(country.getName());

		if (countryRecovered != null) {
			country = null;
		} else {
			country = countryRepository.save(country);
		}

		State stateRecovered = null;
		if (state != null)
			stateRecovered = stateRepository.findByName(state.getName());

		if (state != null && stateRecovered == null) {
			state.setCountry(country == null ? countryRecovered : country);
			state.setCitys(Arrays.asList(city));
			state.setHollyDayDate(Arrays.asList(holliDayDate));
			state = stateRepository.save(state);

		}

		City cityRecovered = null;
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

		holliDay.setHollyDayDate(Arrays.asList(holliDayDate));
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

		HollidayDateId id = new HollidayDateId(dto.getDay(), dto.getMonth(), dto.getYear(),
				country == null ? countryRecovered.getId() : country.getId(), holliDay.getId());

		if (state != null || stateRecovered != null)
			id.setStateId(state == null ? stateRecovered.getId() : state.getId());

		if (city != null || cityRecovered != null)
			id.setCityId(city == null ? cityRecovered.getId() : city.getId());

		holliDayDate.setId(id);

		return holliDayDateRepository.save(holliDayDate);
	}

	public HolliDayDate insert(HolliDayDate holli) {
		return holliDayDateRepository.save(holli);
	}

	public List<HolliDayDate> findAll() {
		return holliDayDateRepository.findAll();
	}

}
