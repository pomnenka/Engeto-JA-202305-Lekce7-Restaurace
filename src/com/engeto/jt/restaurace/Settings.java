package com.engeto.jt.restaurace;

public class Settings {
    public static String getDelimiter() {
        return "\t";
    }

    public static final String PATH_DELIMITER = "/";
//    public static final String FILES_DIRECTORY = "resources/source_files_with_defect";
    public static final String FILES_DIRECTORY = "resources";

    public static final String getVehiclesPathname() {
        return FILES_DIRECTORY + PATH_DELIMITER + "vehicles.csv";
    }

    public static final String getComputersPathname() {
        return FILES_DIRECTORY + PATH_DELIMITER + "computers.csv";
    }

    public static final String getOrdersPathname() {
        return FILES_DIRECTORY + PATH_DELIMITER + "Orders.txt";
    }

    public static final String getPicturesPathname() {
        return FILES_DIRECTORY + PATH_DELIMITER + "Pictures.txt";
    }

    public static final String getCookBookPathname() {
        return FILES_DIRECTORY + PATH_DELIMITER + "CookBook.txt";
    }
}