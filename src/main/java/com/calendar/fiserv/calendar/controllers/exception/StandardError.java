package com.calendar.fiserv.calendar.controllers.exception;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class StandardError implements Serializable{

	private static final long serialVersionUID = 1L;

	private Long timeStamp;
	private Integer status;
	private String message;

}
