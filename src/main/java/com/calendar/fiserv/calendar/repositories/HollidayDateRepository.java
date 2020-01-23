package com.calendar.fiserv.calendar.repositories;

import com.calendar.fiserv.calendar.domain.EHolliDayDate;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface HollidayDateRepository extends JpaRepository<EHolliDayDate, String>, HollidayDateRepositoryCustom {

	@Query("select h from EHolliDayDate h inner join fetch h.holliday inner join fetch h.country left join fetch h.state left join fetch h.city")
	public List<EHolliDayDate> findAllHolliDayDate();

	@Modifying
	@Query(value = "DELETE FROM HOLLIDAY_DATE h where h.country_id = :countryId and h.day = :day and h.month = :month and h.holliday_id = :holliDayId", nativeQuery = true)
	public void remove(Long countryId, Long day, Long holliDayId, Long month);
}
