package ua.yuriydr.hospital.utils;

import java.util.ResourceBundle;

/**
 * This class allows to get a page from pages-config.properties file
 * by their key equivalent.
 */
public class PagesManager {

    /**
     * Static instance of this class.
     */
    private final static ResourceBundle resourceBundle = ResourceBundle.getBundle("pages-config");

    private PagesManager() {

    }

    /**
     * Returns the page by key equivalent.
     *
     * @param key key in pages-config.properties file.
     * @return value (page) from pages-config.properties file.
     */
    public static String getProperty(String key) {
        return resourceBundle.getString(key);
    }

}