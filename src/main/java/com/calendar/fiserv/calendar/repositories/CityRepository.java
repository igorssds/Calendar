package com.calendar.fiserv.calendar.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.calendar.fiserv.calendar.domain.City;

@Repository
public interface CityRepository extends JpaRepository<City, Long>{

	City findByName(String name);

}
