package com.tecacet.movielens.springbatch.converter;

import java.beans.PropertyEditorSupport;

import com.tecacet.movielens.model.Occupation;



public class OccupationPropertyEditor extends PropertyEditorSupport {

	
	@Override
	public void setAsText(String string) throws IllegalArgumentException {
		if (string == null || string.isEmpty()) {
			super.setValue(null);
			return;
		}
		Occupation occupation = Occupation.valueOf(string);
		super.setValue(occupation);
		return;

	}

	

}
