package com.tecacet.movielens.easybathc.converter;

import com.tecacet.movielens.model.Gender;

import org.easybatch.core.converter.TypeConverter;

public class GenderTypeConverter implements TypeConverter<String, Gender> {

    @Override
    public Gender convert(String gender) {
        if (gender == null) {
            return null;
        }
        return gender.equals("M") ? Gender.MALE : gender.equals("F") ? Gender.FEMALE : null;
    }

}
