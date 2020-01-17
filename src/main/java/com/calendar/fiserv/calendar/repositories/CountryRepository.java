package com.calendar.fiserv.calendar.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.calendar.fiserv.calendar.domain.Country;

@Repository
public interface CountryRepository extends JpaRepository<Country, Long>{
	
	public Optional<Country> findByName(String name);

}
