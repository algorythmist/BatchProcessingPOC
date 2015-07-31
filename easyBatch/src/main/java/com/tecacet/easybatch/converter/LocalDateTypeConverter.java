package com.tecacet.easybatch.converter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.easybatch.core.api.TypeConverter;

public class LocalDateTypeConverter implements TypeConverter<LocalDate>{

    //01-Jan-1995
    
    @Override
    public LocalDate convert(String dateString) {
        if (dateString == null || dateString.isEmpty()) {
            return null;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy");
        return  LocalDate.parse(dateString, formatter);
    }

}
