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

import com.calendar.fiserv.calendar.domain.dto.StateDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "state")
public class State {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Column(length = 150)
	private String name;

	@Column(length = 2)
	private String code;

	@NotNull
	@Column(length = 1)
	private char active;

	@NotNull
	private LocalDateTime creationDate;

	private LocalDateTime updateDate;

	@JsonIgnore
	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "country_id")
	private Country country;

	@OneToMany(mappedBy = "state", fetch = FetchType.LAZY)
	private List<City> citys;

	@OneToMany(mappedBy = "state", fetch = FetchType.LAZY)
	private List<HolliDayDate> hollyDayDate;

	public State(StateDTO dto) {
		this.name = dto.getName();
		this.active = dto.getActive();
		this.code = dto.getCode();
		this.creationDate = LocalDateTime.now();
	}
}
