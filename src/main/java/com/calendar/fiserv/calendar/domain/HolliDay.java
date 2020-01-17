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
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.calendar.fiserv.calendar.domain.dto.HolliDayDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "holliday")
public class HolliDay {

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

	@OneToMany(mappedBy = "holliday", fetch = FetchType.LAZY)
	private List<HolliDayDate> hollyDayDate;

	
	
	public HolliDay(HolliDayDTO dto, HolliDayDate holliDayDate) {
		this.name = dto.getName();
		this.active = dto.getActive();
		this.hollyDayDate = Arrays.asList(holliDayDate);
		this.creationDate = LocalDateTime.now();
	}
	
	public HolliDay(HolliDayDTO dto) {
		this.name = dto.getName();
		this.active = dto.getActive();
		this.creationDate = LocalDateTime.now();
	}

	public HolliDay(Long id,  String name,  char active,  LocalDateTime creationDate,
			LocalDateTime updateDate) {
		this.id = id;
		this.name = name;
		this.active = active;
		this.creationDate = creationDate;
		this.updateDate = updateDate;
	}
}
