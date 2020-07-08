package com.tecacet.movielens.springbatch.converter;

import java.beans.PropertyEditorSupport;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Convert String to dates for the benefit of Spring's reflection framework
 */
public class LocalDatePropertyEditor extends PropertyEditorSupport {


    @Override
    public void setAsText(String string) throws IllegalArgumentException {
        if (string == null || string.isEmpty()) {
            super.setValue(null);
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d-MMM-yyyy");
        LocalDate localDate = LocalDate.parse(string, formatter);
        super.setValue(localDate);

    }


}
