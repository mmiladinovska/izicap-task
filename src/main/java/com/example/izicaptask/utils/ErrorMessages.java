package com.example.izicaptask.utils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ErrorMessages {

    public static String errorCallingSireneApiErrorMessage(String api, String endpoint) {
        return String.format("Error calling Sirene %s API: Error calling endpoint: %s", api, endpoint);
    }
}
