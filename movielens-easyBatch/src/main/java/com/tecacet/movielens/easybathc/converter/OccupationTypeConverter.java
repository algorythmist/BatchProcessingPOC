package com.tecacet.movielens.easybathc.converter;

import org.easybatch.core.converter.TypeConverter;

import com.tecacet.movielens.model.Occupation;

public class OccupationTypeConverter implements TypeConverter<String, Occupation> {

    @Override
    public Occupation convert(String occupation) {
        return Occupation.valueOf(Occupation.class, occupation);
    }

}
