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

import com.calendar.fiserv.calendar.domain.dto.StateDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "state")
public class EState {

	@Id
	@SequenceGenerator(name = "stateseq" , sequenceName = "STATE_PK" , allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE , generator = "stateseq")
	private Long id;

	
	@Column(length = 150 , nullable = false)
	private String name;

	@Column(length = 2)
	private String code;

	
	@Column(length = 1 , nullable = false)
	private char active;

	
	@Column(name = "creation_date" , nullable = false)
	private LocalDateTime creationDate;

	@Column(name = "update_date")
	private LocalDateTime updateDate;

	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "country_id" , nullable = false)
	private ECountry country;

	@Column(nullable = true)
	@OneToMany(mappedBy = "state", fetch = FetchType.LAZY)
	private List<ECity> citys;

	@Column(nullable = true)
	@OneToMany(mappedBy = "state", fetch = FetchType.LAZY)
	private List<EHolliDayDate> hollyDayDate;

	public EState(StateDTO dto) {
		this.name = dto.getName();
		this.active = dto.getActive();
		this.code = dto.getCode();
		this.creationDate = LocalDateTime.now();
	}
}
