package com.tecacet.movielens.springbatch.converter;

import com.tecacet.movielens.model.Occupation;

import java.beans.PropertyEditorSupport;

public class OccupationPropertyEditor extends PropertyEditorSupport {


    @Override
    public void setAsText(String string) throws IllegalArgumentException {
        if (string == null || string.isEmpty()) {
            super.setValue(null);
        }
        Occupation occupation = Occupation.valueOf(string);
        super.setValue(occupation);

    }



}
