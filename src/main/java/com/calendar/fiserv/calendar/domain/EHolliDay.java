package com.calendar.fiserv.calendar.domain;

import java.time.LocalDateTime;
import java.util.Arrays;
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

import com.calendar.fiserv.calendar.domain.dto.HolliDayDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "holliday")
public class EHolliDay {

	@Id
	@SequenceGenerator(name = "holliDayseq" , sequenceName = "HOLLIDAY_PK" , allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE , generator = "holliDayseq")
	private Long id;

	@NotNull
	@Column(length = 150)
	private String name;

	@NotNull
	@Column(length = 1)
	private char active;

	@NotNull
	@Column(name = "creation_date")
	private LocalDateTime creationDate;

	@Column(name = "update_date")
	private LocalDateTime updateDate;

	@JsonIgnore
	@OneToMany(mappedBy = "holliday", fetch = FetchType.LAZY)
	private List<EHolliDayDate> hollyDayDate;

	
	
	public EHolliDay(HolliDayDTO dto, EHolliDayDate holliDayDate) {
		this.name = dto.getName();
		this.active = dto.getActive();
		this.hollyDayDate = Arrays.asList(holliDayDate);
		this.creationDate = LocalDateTime.now();
	}
	
	public EHolliDay(HolliDayDTO dto) {
		this.name = dto.getName();
		this.active = dto.getActive();
		this.creationDate = LocalDateTime.now();
	}

	public EHolliDay(Long id,  String name,  char active,  LocalDateTime creationDate,
			LocalDateTime updateDate) {
		this.id = id;
		this.name = name;
		this.active = active;
		this.creationDate = creationDate;
		this.updateDate = updateDate;
	}
}
