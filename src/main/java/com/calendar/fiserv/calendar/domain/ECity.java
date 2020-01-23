package com.calendar.fiserv.calendar.domain;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.calendar.fiserv.calendar.domain.dto.CityDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "city")
public class ECity {

	@Id
	@SequenceGenerator(name = "cityseq" , sequenceName = "CITY_PK" , allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE , generator = "cityseq")
	private Long id;

	@Column(length = 150 , nullable = false)
	private String name;

	
	@Column(length = 1 , nullable = false)
	private char active;

	
	@Column(name = "creation_date" , nullable = false)
	private LocalDateTime creationDate;

	@Column(name = "update_date")
	private LocalDateTime updateDate;

	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "state_id", insertable = false, updatable = false)
	private EState state;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "country_id", insertable = false, updatable = false)
	private ECountry country;

	public ECity(CityDTO dto) {
		this.name = dto.getName();
		this.active = dto.getActive();
		this.creationDate = LocalDateTime.now();
	}

}
