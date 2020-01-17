package com.calendar.fiserv.calendar.services;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.calendar.fiserv.calendar.domain.City;
import com.calendar.fiserv.calendar.domain.Country;
import com.calendar.fiserv.calendar.domain.HolliDay;
import com.calendar.fiserv.calendar.domain.HolliDayDate;
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

		Optional<Country> optionalCountry = countryRepository.findByName(dto.getCountry().getName().toUpperCase());

		if (!optionalCountry.isPresent()) {
			Country country = new Country(null, dto.getCountry().getName().toUpperCase(),
					dto.getCountry().getCode().toUpperCase(), dto.getCountry().getActive(), LocalDateTime.now(), null,
					dto.getCountry().getHasState(), null, null, null);

			State state = new State(null, dto.getState().getName(), dto.getState().getCode(),
					dto.getState().getActive(), LocalDateTime.now(), null, country, null, null);

			City city = new City(null, dto.getCity().getName(), dto.getCity().getActive(), LocalDateTime.now(), null,
					state, country, null);

			HolliDay holliDay = new HolliDay(null, dto.getHolliday().getName(), dto.getHolliday().getActive(),
					LocalDateTime.now(), null);

			HolliDayDate holliDayDate = new HolliDayDate(null, dto.getActive(), LocalDateTime.now(), null, holliDay,
					country, state, city);

			country.setStates(Arrays.asList(state));
			country.setCitys(Arrays.asList(city));
			country.setHollyDayDate(Arrays.asList(holliDayDate));

			state.setCitys(Arrays.asList(city));
			state.setHollyDayDate(Arrays.asList(holliDayDate));

			city.setHollyDayDate(Arrays.asList(holliDayDate));

			holliDay.setHollyDayDate(Arrays.asList(holliDayDate));

			return holliDayDate;
		}

		Country country = optionalCountry.get();

		Optional<State> optionalState = stateRepository.findByCountryId(country.getId());
		return null;
	}

	public HolliDayDate insert(HolliDayDate holli) {
		return holliDayDateRepository.save(holli);
	}
}
