package com.tecacet.movielens.easybathc.converter;

import org.easybatch.core.api.TypeConverter;

import com.tecacet.movielens.model.Gender;

public class GenderTypeConverter implements TypeConverter<Gender> {

    @Override
    public Gender convert(String gender) {
        if (gender == null) {
            return null;
        }
        return gender.equals("M") ? Gender.MALE : gender.equals("F") ? Gender.FEMALE : null;
    }

}
