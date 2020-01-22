package com.calendar.fiserv.calendar.repositories;

import com.calendar.fiserv.calendar.domain.EHolliDayDate;
import org.hibernate.jpa.TypedParameterValue;
import org.hibernate.type.LongType;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Parameter;
import javax.persistence.Query;

@Repository
public class HollidayDateRepositoryImpl implements HollidayDateRepositoryCustom {
    private final EntityManager entityManager;

    public HollidayDateRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void insert(EHolliDayDate hdd) {
        Query query = entityManager.createNativeQuery("INSERT INTO HOLLIDAY_DATE (COUNTRY_ID,STATE_ID,CITY_ID,HOLLIDAY_ID,\"DAY\",\"MONTH\"," +
            "\"YEAR\",ACTIVE,CREATION_DATE) VALUES (:countryId, :stateId, :cityId, :hollidayId, :day, :month, :year, '1', SYSDATE)")
            .setParameter("countryId", hdd.getCountry().getId())
            .setParameter("stateId", new TypedParameterValue(StandardBasicTypes.LONG, hdd.getState() != null ? hdd.getState().getId() : null))
            .setParameter("cityId", new TypedParameterValue(StandardBasicTypes.LONG, hdd.getCity() != null ? hdd.getCity().getId() : null))
            .setParameter("hollidayId", hdd.getHolliday().getId())
            .setParameter("day", hdd.getDay())
            .setParameter("month", hdd.getMonth())
            .setParameter("year", new TypedParameterValue(StandardBasicTypes.LONG, hdd.getYear()))
            ;

        query.executeUpdate();
    }
}
