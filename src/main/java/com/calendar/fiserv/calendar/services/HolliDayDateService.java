package com.calendar.fiserv.calendar.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.calendar.fiserv.calendar.domain.ECity;
import com.calendar.fiserv.calendar.domain.ECountry;
import com.calendar.fiserv.calendar.domain.EHolliDay;
import com.calendar.fiserv.calendar.domain.EHolliDayDate;
import com.calendar.fiserv.calendar.domain.EState;
import com.calendar.fiserv.calendar.domain.dto.CityDTO;
import com.calendar.fiserv.calendar.domain.dto.CountryDTO;
import com.calendar.fiserv.calendar.domain.dto.HolliDayDTO;
import com.calendar.fiserv.calendar.domain.dto.HolliDayDateDTO;
import com.calendar.fiserv.calendar.domain.dto.StateDTO;
import com.calendar.fiserv.calendar.repositories.HolliDayRepository;
import com.calendar.fiserv.calendar.repositories.HollidayDateRepository;
import com.calendar.fiserv.calendar.services.dto.HolliDayDateRemoveDTO;
import com.calendar.fiserv.calendar.services.dto.HolliDayDateUpdateDTO;

@Service
public class HolliDayDateService {

	@Autowired
	private HollidayDateRepository holliDayDateRepository;

	@Autowired
	private HolliDayRepository holliDayRepository;

	@Transactional
	public void fromDTO(HolliDayDateDTO dto, ECountry country, EState state, ECity city, EHolliDay holliDay) {

		EHolliDayDate holliDayDate = holliDayDateRepository.findByHolliday(country.getId(), holliDay.getId(),
				dto.getDay(), dto.getMonth());

		if (holliDayDate == null) {
			holliDayDate = new EHolliDayDate(null, dto.getDay(), dto.getMonth(), dto.getYear(), dto.getActive(),
					LocalDateTime.now(), null, holliDay, country, state, city);
		}
		holliDayDateRepository.insert(holliDayDate);

	}

	public List<HolliDayDateDTO> findAll() {
		List<EHolliDayDate> holliDaysDate = holliDayDateRepository.findAllHolliDayDate();
		List<HolliDayDateDTO> listDTO = new ArrayList<>();

		for (EHolliDayDate h : holliDaysDate) {
			HolliDayDateDTO holliDayDateDTO = new HolliDayDateDTO();
			CountryDTO countryDTO = new CountryDTO();
			StateDTO stateDTO = new StateDTO();
			CityDTO cityDTO = new CityDTO();
			HolliDayDTO holliDayDTO = new HolliDayDTO();

			holliDayDateDTO.setActive(h.getActive());
			holliDayDateDTO.setDay(h.getDay());
			holliDayDateDTO.setMonth(h.getMonth());
			holliDayDateDTO.setYear(h.getYear());

			countryDTO.setId(h.getCountry().getId());
			countryDTO.setActive(h.getCountry().getActive());
			countryDTO.setCode(h.getCountry().getCode());
			countryDTO.setName(h.getCountry().getName());
			countryDTO.setHasState(h.getCountry().getHasState());

			if (h.getState() != null) {
				stateDTO.setId(h.getState().getId());
				stateDTO.setName(h.getState().getName());
				stateDTO.setCode(h.getState().getCode());
				stateDTO.setActive(h.getState().getActive());
			}

			if (h.getCity() != null) {
				cityDTO.setId(h.getCity().getId());
				cityDTO.setName(h.getCity().getName());
				cityDTO.setActive(h.getCity().getActive());
			}

			holliDayDTO.setId(h.getHolliday().getId());
			holliDayDTO.setName(h.getHolliday().getName());
			holliDayDTO.setActive(h.getHolliday().getActive());

			holliDayDateDTO.setCountry(countryDTO);
			holliDayDateDTO.setState(stateDTO);
			holliDayDateDTO.setCity(cityDTO);
			holliDayDateDTO.setHolliday(holliDayDTO);

			listDTO.add(holliDayDateDTO);
		}

		return listDTO;
	}

	@Transactional
	public void remove(HolliDayDateRemoveDTO dto) {
		holliDayDateRepository.remove(dto.getCountryId(), dto.getDay(), dto.getHolliDayId(), dto.getMonth());
		holliDayRepository.deleteById(dto.getHolliDayId());

	}

	@Transactional
	public void update(HolliDayDateUpdateDTO dto) {
		holliDayDateRepository.update(dto);
	}

}
