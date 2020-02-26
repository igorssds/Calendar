package com.calendar.fiserv.calendar.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "holiday")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EHoliday {

	@Id
	@SequenceGenerator(name = "holiday_sequence_generator", sequenceName = "HOLIDAY_SEQUENCE", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "holiday_sequence_generator")
	@Column(name = "id")
	private Long id;

	@Column(name = "name")
	private String name;

	@Column(name = "name_plain_text")
	private String namePlainText;

	@Column(name = "holiday_date")
	private LocalDate date;

	@Column(name = "city_name")
	private String cityName;

	@Column(name = "city_name_plain_text")
	private String cityNamePlainText;

	@Column(name = "state_name")
	private String stateName;

	@Column(name = "state_code")
	private String stateCode;

	@Column(name = "country_name")
	private String countryName;

	@Column(name = "country_code")
	private String countryCode;

	@Column(name = "active")
	private boolean active;

	@Column(name = "creation_date")
	private LocalDateTime creationDate;

	@Column(name = "update_date")
	private LocalDateTime updateDate;

}
