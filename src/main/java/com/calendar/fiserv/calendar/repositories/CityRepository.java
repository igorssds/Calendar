package com.calendar.fiserv.calendar.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.calendar.fiserv.calendar.domain.ECity;

@Repository
public interface CityRepository extends JpaRepository<ECity, Long>{

	ECity findByName(String name);

}
