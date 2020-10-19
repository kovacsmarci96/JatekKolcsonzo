package com.jatekkolcsonzo.server.jdbc.model;

import com.jatekkolcsonzo.server.helper.DateParser;
import com.jatekkolcsonzo.shared.Assertation.Assert;

import java.time.Duration;
import java.time.LocalDate;

/**
 * @author Marton Kovacs
 * @since 2019-10-28
 */
public class Reservation {
    private int id;
    private LocalDate start;
    private LocalDate end;
    private int price;
    private int gameID;

    public Reservation(LocalDate pStart, LocalDate pEnd) {
        Assert.whenNull(pStart, "Start is null");
        Assert.whenNull(pEnd, "Start is null");
        Assert.whenInvalidStartDate(DateParser.parseLocalDatetoDate(pStart)
                , "Nem lehet a kezdeti érték a mai napnál előbb"
        );
        Assert.whenInvalidEndDate(DateParser.parseLocalDatetoDate(pStart),
                DateParser.parseLocalDatetoDate(pEnd),
                "Hibás utolsó nap"
        );

        this.start = pStart;
        this.end = pEnd;
        this.price = (int) Duration.between(pStart.atStartOfDay(), pEnd.atStartOfDay()).toDays() * 500;
    }

    public Reservation(int pId, LocalDate pStart, LocalDate pEnd, int pPrice, int pGameID) {
        Assert.whenInvalidID(pId, "Hibás ID");
        Assert.whenNull(pStart,"Start is null");
        Assert.whenNull(pEnd, "End is null");
        Assert.whenInvalidStartDate(DateParser.parseLocalDatetoDate(pStart)
                , "Nem lehet a kezdeti érték a mai napnál előbb"
        );
        Assert.whenInvalidEndDate(DateParser.parseLocalDatetoDate(pStart),
                DateParser.parseLocalDatetoDate(pEnd),
                "Hibás utolsó nap"
        );
        Assert.whenInvalidID(pGameID, "Hibás ID");
        Assert.whenInvalidPrice(pPrice, "Nem lehet kisebb az összeg 500-nál!");

        this.id = pId;
        this.start = pStart;
        this.end = pEnd;
        this.gameID = pGameID;
        this.price = pPrice;
    }

    public Reservation() {
    }

    public int getId() {
        return id;
    }

    public LocalDate getStart() {
        return start;
    }

    public LocalDate getEnd() {
        return end;
    }

    public int getPrice() {
        return price;
    }

    public int getGameID() {
        return gameID;
    }

    public void setID(int pId) {
        Assert.whenInvalidID(pId, "Hibás ID");

        this.id = pId;
    }

    public void setStart(LocalDate pStart) {
        Assert.whenNull(pStart, "Start is null");
        Assert.whenInvalidStartDate(DateParser.parseLocalDatetoDate(pStart)
                , "Nem lehet a kezdeti érték a mai napnál előbb"
        );
        this.start = pStart;
    }

    public void setEnd(LocalDate pStart, LocalDate pEnd) {
        Assert.whenNull(pStart, "Start is Null");
        Assert.whenNull(pEnd, "End is Null");
        Assert.whenInvalidEndDate(DateParser.parseLocalDatetoDate(pStart),
                DateParser.parseLocalDatetoDate(pEnd),
                "Hibás utolsó nap"
        );
        this.end = pEnd;
    }

    public void setPrice(int pPrice) {
        Assert.whenInvalidPrice(pPrice, "Nem lehet kisebb az összeg 500-nál!");
        this.price = pPrice;
    }

    public void setGameID(int pGameID) {
        Assert.whenInvalidID(pGameID, "Hibás ID");
        this.gameID = pGameID;
    }
}
