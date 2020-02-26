package com.calendar.fiserv.calendar.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.calendar.fiserv.calendar.model.EHoliday;

@Repository
public interface HolidayRepository extends JpaRepository<EHoliday, Long> {

	/**
	 * 
	 * Check if the holiday exists in the database.
	 * 
	 * @param namePlainText
	 * @param date
	 * @param cityNamePlainText
	 * @param stateCode
	 * @param countryCode
	 * @return
	 */
	@Query("SELECT h FROM EHoliday h WHERE h.namePlainText = :namePlainText AND h.date = :date AND (h.cityNamePlainText IS null OR h.cityNamePlainText = :cityNamePlainText) AND (h.stateCode IS null OR h.stateCode = :stateCode) AND h.countryCode = :countryCode")
	public List<EHoliday> exists(String namePlainText, LocalDate date, String cityNamePlainText,
			String stateCode, String countryCode);

	/**
	 * Get all registered holidays.
	 * @return
	 */
	@Query("SELECT h FROM EHoliday h ORDER BY id")
	public List<EHoliday> findAllHolidays();

	/**
	 * 
	 * Check if the holiday exists, given a date and a location.
	 * 
	 * @param date
	 * @param cityNamePlainText
	 * @param stateCode
	 * @param countryCode
	 * @return
	 */
	@Query("SELECT h FROM EHoliday h WHERE h.date = :date AND (:cityNamePlainText IS null OR h.cityNamePlainText = :cityNamePlainText) AND (:stateCode IS null OR h.stateCode = :stateCode) AND h.countryCode = :countryCode")
	public List<EHoliday> findByDateAndLocation(LocalDate date, String cityNamePlainText, String stateCode,
			String countryCode);

}
