package com.calendar.fiserv.calendar.domain;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.calendar.fiserv.calendar.domain.dto.HolliDayDateDTO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "HOLLIDAY_DATE")
public class EHolliDayDate {

	@Id
	@Column(name = "ROWID" , insertable = false , updatable = false)
	private String id;

	@Column(nullable = false)
	private Long day;

	@Column(nullable = false)
	private Long month;

	private Long year;

	@NotNull
	private char active;

	@Column(name = "CREATION_DATE", nullable = false)
	private LocalDateTime creationDate;

	@Column(name = "update_date")
	private LocalDateTime updateDate;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "holliday_id", nullable = false)
	private EHolliDay holliday;

    @JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "country_id", nullable = false)
	private ECountry country;

    @JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "state_id")
	private EState state;

    @JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "city_id")
	private ECity city;

	public EHolliDayDate(HolliDayDateDTO dto) {
		this.active = dto.getActive();
		this.day = dto.getDay();
		this.month = dto.getMonth();
		this.year = dto.getYear();
		this.creationDate = LocalDateTime.now();
		this.holliday = new EHolliDay(dto.getHolliday());
		this.country = new ECountry(dto.getCountry());
		this.state = new EState(dto.getState());
		this.city = new ECity(dto.getCity());
	}

	public EHolliDayDate(HolliDayDateDTO dto, EHolliDay holliDay2, ECountry country, EState state, ECity city) {

	}

}
