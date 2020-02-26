package com.calendar.fiserv.calendar.services;

import java.text.Normalizer;
import java.text.Normalizer.Form;

import org.springframework.stereotype.Service;

@Service
public class StringNormalizer {

	public static String toPlainTextUpperCase(String dirty) {
		
		if (dirty == null || dirty.trim().isEmpty()) {
			return null;
		}

		String clean = Normalizer.normalize(dirty, Form.NFD).replaceAll("[^\\p{ASCII}]", "").toUpperCase();
		return clean;
	}

}
