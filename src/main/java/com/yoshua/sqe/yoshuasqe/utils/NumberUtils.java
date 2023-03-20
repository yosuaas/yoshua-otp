package com.yoshua.sqe.yoshuasqe.utils;

import java.util.Random;

public class NumberUtils {
    
    private static final String OTP_COMPONENT = "1234567890";

    public static String generateOtp() {

        StringBuilder sb = new StringBuilder();

        Random r = new Random();
        for (int i=0; i<6; i++) {
            sb.append(OTP_COMPONENT.charAt(r.nextInt(OTP_COMPONENT.length())));
        }

        return sb.toString();
    }

}
