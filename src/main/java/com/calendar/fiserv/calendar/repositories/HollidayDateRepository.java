package com.calendar.fiserv.calendar.repositories;

import com.calendar.fiserv.calendar.domain.EHolliDayDate;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface HollidayDateRepository extends JpaRepository<EHolliDayDate, String>, HollidayDateRepositoryCustom {


	@Query("select h from EHolliDayDate h inner join fetch h.holliday inner join fetch h.country inner join fetch h.state inner join fetch h.city")
	public List<EHolliDayDate> findAllHolliDayDate();
}
