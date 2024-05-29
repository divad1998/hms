package com.ingryd.hms.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

public class Helper {

    private static final Logger logger = LoggerFactory.getLogger(Helper.class);

    public static final ObjectMapper objectMapper = new ObjectMapper();

    public static void log (String message) {
        logger.info(message);
    }

    public static <T> boolean isEmpty(T value) {
        if (value == null)
            return true;
        if (value instanceof String && ((String) value).trim().isEmpty())
            return true;
        return false;
    }

    public static ZoneOffset getTimeZone() {
        return ZoneOffset.of("+1");
    }

    public static LocalDateTime convertToLocalDateTime(Date dateToConvert) {
        if (Helper.isEmpty(dateToConvert))
            return null;
        return dateToConvert.toInstant().atZone(getTimeZone()).toLocalDateTime();
    }


}
