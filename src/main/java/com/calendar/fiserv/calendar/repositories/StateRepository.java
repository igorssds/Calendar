package com.calendar.fiserv.calendar.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.calendar.fiserv.calendar.domain.State;

@Repository
public interface StateRepository extends JpaRepository<State, Long>{

	Optional<State> findByCountryId(Long id);

}
