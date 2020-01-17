package com.calendar.fiserv.calendar.domain;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.calendar.fiserv.calendar.domain.dto.CountryDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "country")
public class Country {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Column(length = 100)
	private String name;

	@Column(length = 2)
	private String code;

	@Column(length = 1)
	private char active;

	@NotNull
	private LocalDateTime creationDate;

	private LocalDateTime updateDate;

	@Column(length = 1)
	private char hasState;

	@OneToMany(mappedBy = "country", fetch = FetchType.LAZY)
	private List<State> states;

	@OneToMany(mappedBy = "country", fetch = FetchType.LAZY)
	private List<City> citys;

	@OneToMany(mappedBy = "country", fetch = FetchType.LAZY)
	private List<HolliDayDate> hollyDayDate;
	
	public Country(CountryDTO dto) {
		this.name = dto.getName();
		this.code = dto.getCode();
		this.active = dto.getActive();
		this.hasState = dto.getHasState();
		this.creationDate = LocalDateTime.now();
	}
}
