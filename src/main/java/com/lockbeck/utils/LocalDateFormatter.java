package com.lockbeck.utils;

import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Component
public class LocalDateFormatter {
    public LocalDate getLocalDate(String date) {


        if (date != null) {

            // Parse the date string into an Instant
            Instant instant = Instant.parse(date);

            // Convert the Instant to a LocalDate
            return  instant.atZone(ZoneId.of("UTC")).toLocalDate();


        }else {
            return null;
        }
    }



    public LocalDate getLocalDateForUpdates(String date) {
        // Define DateTimeFormatter for both formats
        DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX");
        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        // Attempt to parse the date string with both formatters
        try {
            // Try parsing with formatter1
            return LocalDate.parse(date, formatter1);
        } catch (DateTimeParseException e1) {
            try {
                // Try parsing with formatter2
                return LocalDate.parse(date, formatter2);
            } catch (DateTimeParseException e2) {
                // If parsing fails with both formatters, throw an exception or return null, depending on your requirements
                throw new IllegalArgumentException("Unsupported date format mall: " + date);
                // Alternatively, you could return null if you prefer
                // return null;
            }
        }
    }

    public String getStringDate(LocalDate date) {

        if (date!=null) {


            // Create a LocalDateTime object by combining the LocalDate with midnight time
            LocalDateTime localDateTime = date.atStartOfDay();

            // Define the formatter to format the output string
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            // Format the LocalDateTime object into a string
            String formattedDate = localDateTime.format(formatter);
            return formattedDate;
        }
        else {
            return null;
        }
    }
}
