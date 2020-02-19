package com.calendar.fiserv.calendar.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.calendar.fiserv.calendar.domain.EHolliDayDate;

@Repository
public interface HollidayDateRepository extends JpaRepository<EHolliDayDate, String>, HollidayDateRepositoryCustom {

	@Query("select h from EHolliDayDate h inner join fetch h.holliday inner join fetch h.country left join fetch h.state left join fetch h.city where h.holliday.active = 1 and h.active = 1")
	public List<EHolliDayDate> findAllHolliDayDate();

	@Modifying
	@Query(value = "DELETE FROM HOLLIDAY_DATE h where h.country_id = :countryId and h.day = :day and h.month = :month and h.holliday_id = :holliDayId", nativeQuery = true)
	public void remove(Long countryId, Long day, Long holliDayId, Long month);

	@Query("select h from EHolliDayDate h where h.country.id = :countryId and h.holliday.id = :hollidayId and h.day = :day and h.month = :month")
	public EHolliDayDate findByHolliday(Long countryId, Long hollidayId, Integer day, Integer month);

	@Query("SELECT h FROM EHolliDayDate h WHERE h.year = :hYear AND h.month = :hMonth AND h.day = :hDay")
	public Optional<EHolliDayDate> findByDate(Integer hYear, Integer hMonth, Integer hDay);
}
