package com.calendar.fiserv.calendar.repositories;

import com.calendar.fiserv.calendar.domain.EHolliDayDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HollidayDateRepository extends JpaRepository<EHolliDayDate, String>, HollidayDateRepositoryCustom {


}
