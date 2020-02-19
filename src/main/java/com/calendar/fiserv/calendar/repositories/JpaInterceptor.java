package com.calendar.fiserv.calendar.repositories;

import java.io.Serializable;

import org.hibernate.EmptyInterceptor;
import org.hibernate.type.Type;

import com.calendar.fiserv.calendar.domain.ECity;
import com.calendar.fiserv.calendar.domain.EState;
import com.calendar.fiserv.calendar.services.StringNormalizer;

public class JpaInterceptor extends EmptyInterceptor {

	/**
	 * 1L
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public boolean onSave(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) {

		if (entity instanceof ECity) {
			ECity city = (ECity) entity;
			city.setName(StringNormalizer.toPlainTextUpperCase(city.getName()));
		}
		
		if(entity instanceof EState) {
			EState eState = (EState) entity;
			eState.setCode(StringNormalizer.toPlainTextUpperCase(eState.getCode()));
		}
		
		

		return super.onSave(entity, id, state, propertyNames, types);
	}

}
