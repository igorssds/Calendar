package com.calendar.fiserv.calendar.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.calendar.fiserv.calendar.domain.EState;

@Repository
public interface StateRepository extends JpaRepository<EState, Long>{

	Optional<EState> findByCountryId(Long id);

	EState findByName(String name);

	EState findByCode(String plainTextStateCode);

}
