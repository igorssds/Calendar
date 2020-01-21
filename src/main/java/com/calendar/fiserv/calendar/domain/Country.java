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
import javax.persistence.SequenceGenerator;
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
	@SequenceGenerator(name = "countryseq" , sequenceName = "COUNTRY_PK" , allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE , generator = "countryseq")
	private Long id;

	@Column(length = 100 , nullable = false)
	private String name;

	@Column(length = 2)
	private String code;

	@Column(length = 1)
	private char active;

	@Column(name = "creation_date" , nullable = false)
	private LocalDateTime creationDate;

	@Column(name = "update_date")
	private LocalDateTime updateDate;

	@Column(length = 1 ,name = "has_state")
	private char hasState;


	@Column(nullable = true)
	@OneToMany(mappedBy = "country", fetch = FetchType.LAZY)
	private List<State> states;

	@Column(nullable = true)
	@OneToMany(mappedBy = "country", fetch = FetchType.LAZY)
	private List<City> citys;

	@Column(nullable = true)
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
