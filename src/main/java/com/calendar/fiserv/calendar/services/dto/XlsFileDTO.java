package com.calendar.fiserv.calendar.services.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class XlsFileDTO {

	private byte[] file;
	private boolean withErrors;
	
}
