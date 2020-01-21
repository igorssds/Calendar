package com.calendar.fiserv.calendar.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.calendar.fiserv.calendar.domain.ECountry;

@Repository
public interface CountryRepository extends JpaRepository<ECountry, Long> {

	public ECountry findByName(String name);

}
