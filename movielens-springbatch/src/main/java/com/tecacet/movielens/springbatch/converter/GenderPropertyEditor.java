package com.tecacet.movielens.springbatch.converter;

import com.tecacet.movielens.model.Gender;

import java.beans.PropertyEditorSupport;

public class GenderPropertyEditor extends PropertyEditorSupport {

    @Override
    public void setAsText(String string) throws IllegalArgumentException {
        if (string == null || string.isEmpty()) {
            super.setValue(null);
        }
        Gender gender = string.equals("M") ? Gender.MALE : string.equals("F") ? Gender.FEMALE : null;
        super.setValue(gender);

    }



}
