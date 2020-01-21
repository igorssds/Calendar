package com.calendar.fiserv.calendar.domain;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.calendar.fiserv.calendar.domain.dto.HolliDayDateDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "HOLLIDAY_DATE")
public class HolliDayDate {

	@EmbeddedId
	private HollidayDateId id;

	@NotNull
	private char active;


	@Column(name = "CREATION_DATE" , nullable = false)
	private LocalDateTime creationDate;

	@Column(name = "update_date")
	private LocalDateTime updateDate;

	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "holliday_id", insertable = false, updatable = false)
	private HolliDay holliday;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "country_id", insertable = false, updatable = false)
	private Country country;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "state_id", insertable = false, updatable = false)
	private State state;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "city_id", insertable = false, updatable = false)
	private City city;

	public HolliDayDate(HolliDayDateDTO dto) {
		this.active = dto.getActive();
		HollidayDateId id = new HollidayDateId(dto);
		this.id = id;
		this.id.day = dto.getDay();
		this.id.month = dto.getMonth();
		this.id.year = dto.getYear();
		this.creationDate = LocalDateTime.now();
		this.holliday = new HolliDay(dto.getHolliday());
		this.country = new Country(dto.getCountry());
		this.state = new State(dto.getState());
		this.city = new City(dto.getCity());
	}

	@Data
	@EqualsAndHashCode
	@Embeddable
	public static class HollidayDateId implements Serializable {

		private static final long serialVersionUID = 1L;

		@Column(nullable = false)
		private Long day;

		@Column(nullable = false)
		private Long month;

		private Long year;

		@Column(name = "country_id")
		private Long countryId;

		@Column(name = "state_id", nullable = true)
		private Long stateId;

		@Column(name = "city_id", nullable = true)
		private Long cityId;

		@Column(name = "holliday_id")
		private Long hollidayId;

		public HollidayDateId(HolliDayDateDTO dto) {
			this.day = dto.getDay();
			this.month = dto.getMonth();
			this.year = dto.getYear();
		}

		public HollidayDateId() {
		}

		public HollidayDateId(Long day, Long month, Long year, Long countryId, Long hollidayId) {
			this.day = day;
			this.month = month;
			this.year = year;
			this.countryId = countryId;
			this.hollidayId = hollidayId;
		}

		public HollidayDateId(HollidayDateId id) {
			this.cityId = id.getCityId();
			this.countryId = id.getCountryId();
			this.day = id.getDay();
			this.hollidayId = id.hollidayId;
			this.month = id.getMonth();
			this.stateId = id.getStateId();
			this.year = id.getYear();
		}

	}

}
