package com.calendar.fiserv.calendar.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.calendar.fiserv.calendar.domain.HolliDayDate;
import com.calendar.fiserv.calendar.domain.HolliDayDate.HollidayDateId;

@Repository
public interface HollidayDateRepository extends JpaRepository<HolliDayDate, HollidayDateId>{

}