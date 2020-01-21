package com.calendar.fiserv.calendar.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.calendar.fiserv.calendar.domain.EHolliDay;

@Repository
public interface HolliDayRepository extends JpaRepository<EHolliDay, Long>{

}
