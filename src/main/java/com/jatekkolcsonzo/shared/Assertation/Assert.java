package com.jatekkolcsonzo.shared.Assertation;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author Marton Kovacs
 * @since 2019-11-12
 */
public final class Assert {


    public static void whenNull(Object pObject, String pMessage) {
        if (pObject == null) {
            throw new IllegalArgumentException(pMessage);
        }
    }

    public static void whenNotNull(Object pObject, String pMessage) {
        if (pObject != null) {
            throw new IllegalArgumentException(pMessage);
        }
    }



    public static void whenEmptyList(List pList, String pMessage) {
        if (pList.isEmpty()) {
            throw new IllegalArgumentException(pMessage);
        }
    }

    public static void whenEmptyString(String pString, String pMessage) {
        if (pString == null || pString.isEmpty()) {
            throw new IllegalArgumentException(pMessage);
        }
    }

    public static void whenInvalidID(int pID, String pMessage) {
        if (pID < 0) {
            throw new IllegalArgumentException(pMessage);
        }
    }

    public static void whenInvalidPhoneNumber(String pPhoneNumber, String pMessage) {

    }

    public static void whenInvalidEmail(String pEmail, String pMessage) {

    }

    public static void whenInvalidName(String pName, String pMessage){

    }

    public static void whenInvalidString(String pString, String pMessage) {

    }

    public static void whenInvalidStartDate(Date pStartDate, String pMessage) {
        long today = System.currentTimeMillis() / (60*60*24*1000);
        long startDate = pStartDate.getTime() / (60*60*24*1000);

        if (startDate + 1 < today) {
            throw new IllegalArgumentException(pMessage);
        }
    }

    public static void whenInvalidEndDate(Date pStartDate, Date pEndDate, String pMessage) {
        long diff = pEndDate.getTime() - pStartDate.getTime();
        long diff1 = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
        if (diff1 > 14 || pEndDate.getTime() <= pStartDate.getTime()) {
            throw new IllegalArgumentException(pMessage);
        }
    }

    public static void whenInvalidPrice(int pPrice, String pMessage) {
        if (pPrice < 500) {
            throw new IllegalArgumentException(pMessage);
        }
    }


}