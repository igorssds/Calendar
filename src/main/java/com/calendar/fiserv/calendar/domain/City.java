package com.calendar.fiserv.calendar.domain;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.calendar.fiserv.calendar.domain.dto.CityDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "city")
public class City {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Column(length = 150)
	private String name;

	@NotNull
	@Column(length = 1)
	private char active;

	@NotNull
	private LocalDateTime creationDate;

	private LocalDateTime updateDate;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "state_id")
	private State state;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "country_id")
	private Country country;

	@OneToMany(mappedBy = "city", fetch = FetchType.LAZY)
	private List<HolliDayDate> hollyDayDate;
	
	public City(CityDTO dto) {
		this.name = dto.getName();
		this.active = dto.getActive();
		this.creationDate = LocalDateTime.now();
	}

}
