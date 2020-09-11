package com.andela.gads2020leaderboard.utils;

import android.util.Patterns;

import java.util.regex.Pattern;

/**
 * Validation for forms fields
 *
 * @author Bertrand TCHUENTE
 */
public class Validation {
    // Regular Expression
    // you can change the expression based on your need
    private static final String EMAIL_REGEX = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    // A placeholder username validation check
    public static boolean isEmailAddressNotValid(String emailAddress) {
        if (emailAddress == null || emailAddress.trim().isEmpty() || !emailAddress.contains("@")) return true;
        return !Patterns.EMAIL_ADDRESS.matcher(emailAddress).matches();
    }

    public static boolean isTextNotValid(String text) {
        return text == null || text.trim().isEmpty();
    }

    public static boolean isNameNotValid(String text) {
        return text == null || text.trim().isEmpty() || text.trim().length() <= 3;
    }

    // check if the text is empty or not
    // return true if the text is not empty otherwise false
    private static boolean hasText(String text) {
        // length 0 means there is no text
        return text.trim().length() != 0;
    }

    // return true if the input text is valid, based on the parameter passed
    public static boolean isValid(String text, String regex) {
        return hasText(text.trim()) && Pattern.matches(regex, text.trim());
    }

    public static boolean isTextsEqual(String text1, String text2) {
        return text1.trim().equals(text2.trim());
    }
}
