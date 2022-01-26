package com.example.exxtest.util;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

public class BetaServiceCommonUtil {

    public static String generateGuID(){

        UUID uuid = UUID.randomUUID();
        String randomUUIDString = uuid.toString();



        return randomUUIDString;
    }

    public static String getJsoneString(Object object, ObjectMapper mapper) {
        String strJson = null;
        try {
            strJson = mapper.writeValueAsString(object);
        } catch (JsonGenerationException jsonGenEx) {

        } catch (Exception ex) {
            strJson = "";
        }
        return strJson;
    }

    public static String getCurrentTime(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
        String mydate = format.format(date);

        return mydate;
    }
}
