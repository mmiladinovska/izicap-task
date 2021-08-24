package com.example.izicaptask.utils;

import lombok.experimental.UtilityClass;

import java.io.IOException;

@UtilityClass
public class ErrorMessages {

    public static final String WRITING_TO_CSV_ERROR_MESSAGE = "Error in writing to csv database";

    public static String failParsingToCSVFileErrorMessage(IOException e) {
        return "Fail to parse to CSV file: " + e.getMessage();
    }

    public static String errorCallingSireneApiErrorMessage(String api, String endpoint) {
        return String.format("Error calling Sirene %s API: Error calling endpoint: %s", api, endpoint);
    }

}
