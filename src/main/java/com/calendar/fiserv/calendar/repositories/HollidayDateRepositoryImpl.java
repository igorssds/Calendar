package com.calendar.fiserv.calendar.repositories;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.hibernate.jpa.TypedParameterValue;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Repository;

import com.calendar.fiserv.calendar.domain.EHolliDayDate;
import com.calendar.fiserv.calendar.services.dto.HolliDayDateUpdateDTO;

@Repository
public class HollidayDateRepositoryImpl implements HollidayDateRepositoryCustom {
	private final EntityManager entityManager;

	public HollidayDateRepositoryImpl(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	public void insert(EHolliDayDate hdd) {
		Query query = entityManager.createNativeQuery(
				"INSERT INTO HOLLIDAY_DATE (COUNTRY_ID,STATE_ID,CITY_ID,HOLLIDAY_ID,\"DAY\",\"MONTH\","
						+ "\"YEAR\",ACTIVE,CREATION_DATE) VALUES (:countryId, :stateId, :cityId, :hollidayId, :day, :month, :year, '1', SYSDATE)")
				.setParameter("countryId", hdd.getCountry().getId())
				.setParameter("stateId",
						new TypedParameterValue(StandardBasicTypes.LONG,
								hdd.getState() != null ? hdd.getState().getId() : null))
				.setParameter("cityId",
						new TypedParameterValue(StandardBasicTypes.LONG,
								hdd.getCity() != null ? hdd.getCity().getId() : null))
				.setParameter("hollidayId", hdd.getHolliday().getId()).setParameter("day", hdd.getDay())
				.setParameter("month", hdd.getMonth())
				.setParameter("year", new TypedParameterValue(StandardBasicTypes.LONG, hdd.getYear()));

		query.executeUpdate();
	}

	@Override
	public void update(HolliDayDateUpdateDTO dto) {
		Query query = entityManager.createNativeQuery(
				"UPDATE HOLLIDAY_DATE SET \"DAY\" = :day , \"MONTH\" = :month, \"YEAR\" = :year , ACTIVE = :active , UPDATE_DATE = SYSDATE "
						+ " WHERE country_id = :countryId and day = :dayId and month = :monthId and holliday_id = :holliDayId")
				.setParameter("day", dto.getDay()).setParameter("month", dto.getMonth())
				.setParameter("year", dto.getYear()).setParameter("active", dto.getActive())
				.setParameter("countryId", dto.getId().getCountryId()).setParameter("dayId", dto.getId().getDayId())
				.setParameter("monthId", dto.getId().getMonthId())
				.setParameter("holliDayId", dto.getId().getHolliDayId());

		query.executeUpdate();
	}

}
