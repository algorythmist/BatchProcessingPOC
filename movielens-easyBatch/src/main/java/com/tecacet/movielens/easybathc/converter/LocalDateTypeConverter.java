package com.tecacet.movielens.easybathc.converter;

import org.easybatch.core.converter.TypeConverter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LocalDateTypeConverter implements TypeConverter<String, LocalDate> {

    //01-Jan-1995

    @Override
    public LocalDate convert(String dateString) {
        if (dateString == null || dateString.isEmpty()) {
            return null;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d-MMM-yyyy");
        return LocalDate.parse(dateString, formatter);
    }

}
