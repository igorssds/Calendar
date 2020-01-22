package com.calendar.fiserv.calendar.services;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.calendar.fiserv.calendar.domain.ECountry;
import com.calendar.fiserv.calendar.domain.EState;
import com.calendar.fiserv.calendar.domain.dto.StateDTO;
import com.calendar.fiserv.calendar.repositories.StateRepository;

@Service
public class StateService {

	@Autowired
	private StateRepository stateRepository;

	public EState stateFromDTO(StateDTO dto, ECountry country) {

		EState state = stateRepository.findByName(dto.getName());
		if (state == null) {
			state = new EState(null, dto.getName().toUpperCase(), dto.getCode(), dto.getActive(), LocalDateTime.now(),
					null, country, null, null);

			state.setCountry(country);
			state = stateRepository.save(state);
		}

		return state;
	}
}
