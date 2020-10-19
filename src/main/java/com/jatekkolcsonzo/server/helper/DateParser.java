package com.jatekkolcsonzo.server.helper;

import com.jatekkolcsonzo.shared.Assertation.Assert;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Date;

/**
 * @author Marton Kovacs
 * @since 2019-11-12
 */
public class DateParser {
    public static Date parseLocalDatetoDate(LocalDate pDate){
        Assert.whenNull(pDate,"LocalDate Null");
        return Date.from(pDate.atTime(LocalTime.MAX).toInstant(ZoneOffset.MAX));
    }

    public static LocalDate parseDatetoLocalDate(Date pDate){
        Assert.whenNull(pDate,"Date is null");
        return pDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

}
