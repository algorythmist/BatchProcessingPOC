package com.tecacet.movielens.springbatch.converter;

import java.beans.PropertyEditorSupport;

import com.tecacet.movielens.model.Gender;


public class GenderPropertyEditor extends PropertyEditorSupport {
	
	@Override
	public void setAsText(String string) throws IllegalArgumentException {
		if (string == null || string.isEmpty()) {
			super.setValue(null);
			return;
		}
		Gender gender = string.equals("M") ? Gender.MALE : string.equals("F") ? Gender.FEMALE : null;
		super.setValue(gender);
		return;

	}

	

}
