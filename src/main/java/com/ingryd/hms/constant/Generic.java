package com.ingryd.hms.constant;

/**
 * @author chukwuebuka
 */
public interface Generic {

    static String ISSUER = "hsm";

    // reg expression
    String DIGITS_REG_EXT = "\\d+";

    String PHONE_REG_EX = "\\A[0-9]{11}\\z"; // "^\\+?\\d{1,3}?\\s?[0-9]{6,18}";

    String EMAIL_REG_EX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.(?:[a-zA-Z]{2,6})$";

    public static final String HEADER_STRING = "Authorization";

    public static final int TIMEOUT_TIME = 10;






}

