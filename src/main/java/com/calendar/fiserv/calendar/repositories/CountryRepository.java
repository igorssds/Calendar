package com.calendar.fiserv.calendar.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.calendar.fiserv.calendar.domain.Country;

@Repository
public interface CountryRepository extends JpaRepository<Country, Long> {

	public Country findByName(String name);

}
