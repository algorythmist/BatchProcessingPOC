package com.tecacet.movielens.easybathc.converter;

import com.tecacet.movielens.model.Occupation;

import org.easybatch.core.converter.TypeConverter;

public class OccupationTypeConverter implements TypeConverter<String, Occupation> {

    @Override
    public Occupation convert(String occupation) {
        return Occupation.valueOf(Occupation.class, occupation);
    }

}
