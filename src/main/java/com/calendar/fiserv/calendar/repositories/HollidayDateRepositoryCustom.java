package com.calendar.fiserv.calendar.repositories;

import com.calendar.fiserv.calendar.domain.EHolliDayDate;
import com.calendar.fiserv.calendar.services.dto.HolliDayDateUpdateDTO;

public interface HollidayDateRepositoryCustom {
	void insert(EHolliDayDate hdd);

	void update(HolliDayDateUpdateDTO dto);

}
