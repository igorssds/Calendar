package com.calendar.fiserv.calendar.services;

import com.calendar.fiserv.calendar.domain.*;
import com.calendar.fiserv.calendar.domain.dto.HolliDayDateDTO;
import com.calendar.fiserv.calendar.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

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

	    //Verificar ATIVO
        ECountry country = countryRepository.findByName(dto.getCountry().getName().toUpperCase());
        if (country == null)
            country = new ECountry(null, dto.getCountry().getName().toUpperCase(),
                dto.getCountry().getCode().toUpperCase(), dto.getCountry().getActive(), LocalDateTime.now(), null,
                dto.getCountry().getHasState(), null, null, null);

        // VERIFICAR ATIVO E PAÍS
        EState state = stateRepository.findByName(dto.getState().getName());
        if (state == null) {
            state = new EState(null, dto.getState().getName().toUpperCase(), dto.getState().getCode(),
                dto.getState().getActive(), LocalDateTime.now(), null, country, null, null);

            state.setCountry(country);
            state = stateRepository.save(state);
        }

        //VERIFICAR ATIVO, PAIS E ESTADO
        ECity city = cityRepository.findByName(dto.getCity().getName());
        if (city == null) {
            city = new ECity(null, dto.getCity().getName(), dto.getCity().getActive(), LocalDateTime.now(), null, null,
                null);

            city.setState(state);
            city.setCountry(country);
            city = cityRepository.save(city);
        }

        //VERIFICAR SE EXISTE E ATIVO
        EHolliDay holliDay = new EHolliDay(null, dto.getHolliday().getName(), dto.getHolliday().getActive(),
            LocalDateTime.now(), null);
        holliDay = holliDayRepository.save(holliDay);

		EHolliDayDate holliDayDate = new EHolliDayDate(null, dto.getDay(), dto.getMonth(), dto.getYear(),
				dto.getActive(), LocalDateTime.now(), null, holliDay, country, state, city);

		holliDayDate.setCountry(country);
        holliDayDate.setState(state);
        holliDayDate.setCity(city);
		holliDayDate.setHolliday(holliDay);

        holliDayDateRepository.insert(holliDayDate);

        return holliDayDate;
	}

	public List<EHolliDayDate> findAll() {
		return holliDayDateRepository.findAll();
	}

}
