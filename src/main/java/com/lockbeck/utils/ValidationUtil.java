package com.lockbeck.utils;

import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class ValidationUtil {

    public boolean isValidEmail(String email) {
        // Regex pattern for the emails format
        String emailRegex = "^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";

        // Compile the pattern
        Pattern pattern = Pattern.compile(emailRegex);

        // Create a matcher with the emails string
        Matcher matcher = pattern.matcher(email);

        // Return true if the emails matches the pattern, false otherwise
        return matcher.matches();
    }

    public boolean isValidPhone(String phone) {
        // Regex pattern for the emails format
        boolean b = phone.startsWith("+998");



        // Return true if the emails matches the pattern, false otherwise
        return !b;
    }
}
