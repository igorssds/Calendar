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
import javax.persistence.SequenceGenerator;
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
	@SequenceGenerator(name = "stateseq" , sequenceName = "STATE_PK" , allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE , generator = "stateseq")
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
	@Column(name = "creation_date")
	private LocalDateTime creationDate;

	@Column(name = "update_date")
	private LocalDateTime updateDate;

	@JsonIgnore
	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "country_id")
	private Country country;

	@Column(nullable = true)
	@OneToMany(mappedBy = "state", fetch = FetchType.LAZY)
	private List<City> citys;

	@Column(nullable = true)
	@OneToMany(mappedBy = "state", fetch = FetchType.LAZY)
	private List<HolliDayDate> hollyDayDate;

	public State(StateDTO dto) {
		this.name = dto.getName();
		this.active = dto.getActive();
		this.code = dto.getCode();
		this.creationDate = LocalDateTime.now();
	}
}
