package ua.yuriydr.hospital.utils;


import java.util.regex.Pattern;

/**
 * This class ensures that the input parameter in the form valid.
 */
public class UserFormChecker {

    /**
     * Checks that username is valid.
     *
     * @param name user name
     * @return true if user name is valid;
     */
    public static boolean userNameCheck(String name) {
        return Pattern.matches("[A-Z][a-z]*|[А-Я][а-я]*", name);
    }

    /**
     * Checks that user is valid.
     *
     * @param surname user surname
     * @return true if user surname is valid;
     */
    public static boolean userSurnameCheck(String surname) {
        return Pattern.matches("[A-Z][a-z]*|[А-Я][а-я]*", surname);
    }

    /**
     * Checks that user is valid.
     *
     * @param phone user phone
     * @return true if user phone is valid;
     */
    public static boolean userPhoneCheck(String phone) {
        return Pattern.matches("[0-9]{10}", phone);
    }

    /**
     * Checks that user is valid.
     *
     * @param diagnosisName user phone
     * @return true if user diagnosis name is valid;
     */
    public static boolean diagnosisCheck(String diagnosisName) {
        return Pattern.matches("[A-Z][a-z]*|[А-Я][а-я]*", diagnosisName);
    }

}
